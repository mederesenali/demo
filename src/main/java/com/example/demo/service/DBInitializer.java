package com.example.demo.service;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.ProgrammEntity;
import com.example.demo.payload.Category;
import com.example.demo.payload.Programm;
import com.example.demo.payload.Results;
import com.example.demo.payload.YmlCatalog;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProgrammRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Slf4j
public class DBInitializer {
    @Autowired
    RequestSender requestSender;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProgrammRepository programmRepository;
    @Autowired
    ProductRepository productRepository;


    @Value("${url.programs}")
    String urlPrograms;
    @Value("${jwt}")
    String jwt;

    public void initDB() {
        String jwt = getJwt();
        log.info("jwt " + jwt);
        try {
            Response response = requestSender.sendRequest(getProgramsRequest(jwt));
            if (response.isSuccessful()) {
                String responseBody = new String(Objects.requireNonNull(response.body()).byteStream().readAllBytes(), StandardCharsets.UTF_8);
                Results results = convertResponseToObj(responseBody);
                List<Programm> programList = results.getResults();
                List<ProgrammEntity> programEntities = programList.stream()
                        .map(f -> new ProgrammEntity(f.getId(), f.getName(), f.getImage(), f.getGotolink(), f.getProductsXmlLink(), convertCategoryDtoToEntity(f.getCategories())))
                        .toList();
                programmRepository.saveAll(programEntities);
                List<String> productsXMLLink = programList.stream().map(Programm::getProductsXmlLink).filter(Objects::nonNull).toList();
                if (!productsXMLLink.isEmpty()) {
                    int numOfCores = Runtime.getRuntime().availableProcessors();
                    ExecutorService executorService = Executors.newFixedThreadPool(numOfCores);
                    for (String link : productsXMLLink) {
                        executorService.execute(() -> {
                            try {
                                Response productsResponse = requestSender.sendRequest(getProductsRequest(link));
                                JAXBContext jaxbContext = JAXBContext.newInstance(YmlCatalog.class);
                                InputStream productsResponseBody = Objects.requireNonNull(productsResponse.body()).byteStream();
                                String str = new String(Objects.requireNonNull(productsResponse.body()).byteStream().readAllBytes(), StandardCharsets.UTF_8);
                                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                                log.info(str);
                                XMLInputFactory xif = XMLInputFactory.newFactory();
                                xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
                                XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(str));
                                log.info(new String(productsResponseBody.readAllBytes()));
                                YmlCatalog ymlCatalog = (YmlCatalog) unmarshaller.unmarshal(xsr);
                                List<ProductEntity> productEntityList = ymlCatalog.getShop().getOffers().getOffer().stream()
                                        .map(f -> new ProductEntity(null, f.getName(), f.getVendor(), f.getPrice(), f.url, f.picture))
                                        .toList();
                                productRepository.saveAll(productEntityList);
                            } catch (IOException e) {
                                log.info("Error while getting products:", e);
                            } catch (JAXBException e) {
                                log.info("Parsing xml exception", e);
                            } catch (XMLStreamException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    executorService.shutdown();
                    executorService.awaitTermination(3, TimeUnit.MINUTES);
                }
            }
        } catch (IOException e) {
            log.info("Error while getting programs:", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @NotNull
    private Request getProgramsRequest(String jwt) {
        return new Request.Builder()
                .url(urlPrograms)
                .method("GET", null)
                .addHeader("Authorization", jwt)
                .build();
    }

    private Request getProductsRequest(String url) {
        return new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
    }

    private String getJwt() {
        return "Bearer " + jwt;
    }

    private Results convertResponseToObj(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.readValue(json, Results.class);
    }

    @NotNull
    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private List<CategoryEntity> convertCategoryDtoToEntity(List<Category> categories) {
        return categories.stream().map(f -> new CategoryEntity(f.getId(), f.getName(), f.getLanguage(), null)).collect(Collectors.toList());
    }
}
