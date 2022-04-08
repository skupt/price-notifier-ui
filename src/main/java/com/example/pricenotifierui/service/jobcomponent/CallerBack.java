package com.example.pricenotifierui.service.jobcomponent;

import com.example.pricenotifierui.entity.Product;
import com.example.pricenotifierui.entity.Task;
import com.example.pricenotifierui.entity.TaskStatus;
import com.example.pricenotifierui.repository.ProductRepository;
import com.example.pricenotifierui.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@PropertySource("classpath:application.properties")
@Component
public class CallerBack extends Thread implements Shutdownable {
    private boolean isShutdown = false;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("${app.pricenotifier-url}")
    private String priceNotifierBaseUrl;


    public CallerBack(@Autowired ThreadGroup threadGroup) {
        super(threadGroup, "CallerBack");
    }

    public void run() {
        while (!isShutdown) {
            try {
                callParserForSearchTaskResult();
            } catch (Exception e) {
                // todo log
                e.printStackTrace();
            }
        }
    }

    public void setShutdown(boolean status) {
        this.isShutdown = true;
    }

    private void callParserForSearchTaskResult() {
        List<Task> processedTaskList = taskRepository.findAllByTaskStatusLimit(TaskStatus.PROCESSED.name(), 10);

        if (processedTaskList.size() == 0) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // todo log
                e.printStackTrace();
            }
        }
        for (Task task : processedTaskList) {
            String url = priceNotifierBaseUrl + "/api/tasks/" + task.getExternalId() + "/products";
            try {
                getAndSaveTaskProducts(task, url);
            } catch (RestClientException e) {
                // todo log
                task.setTaskStatus(TaskStatus.CALLBACK_FOR_PRODUCTS_FAILED);
                taskRepository.save(task);
            }
        }

    }

    @Transactional
    private void getAndSaveTaskProducts(Task task, String url) {
        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(url, Product[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<Product> productList = Arrays.asList(responseEntity.getBody());
            for (Product product : productList) {
                product.setTask(task);
            }
            productRepository.saveAll(productList);
            task.setTaskStatus(TaskStatus.FINISHED);
            taskRepository.save(task);
        } else {
            task.setTaskStatus(TaskStatus.CALLBACK_FOR_PRODUCTS_FAILED);
            taskRepository.save(task);
        }
    }


}
