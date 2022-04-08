package com.example.pricenotifierui.service;

import com.example.pricenotifierui.entity.SearchTask;
import com.example.pricenotifierui.entity.TaskStatus;
import com.example.pricenotifierui.entity.restclient.SearchTaskDTO;
import com.example.pricenotifierui.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UiService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TaskRepository taskRepository;

    @Value("${app.pricenotifier-url}")
    String parserBaseUrl;

    public SearchTask createNewSearchTask(SearchTaskDTO searchTaskDTO) {
        SearchTask searchTask = modelMapper.map(searchTaskDTO, SearchTask.class);
        String parserUrl = parserBaseUrl + "/api/tasks";
        Long searchTaskExternalId = restTemplate.postForObject(parserUrl, searchTaskDTO, Long.class);
        searchTask.setExternalId(searchTaskExternalId);
        searchTask.setTaskStatus(TaskStatus.NEW);
        searchTask = taskRepository.save(searchTask);

        return searchTask;
    }
}
