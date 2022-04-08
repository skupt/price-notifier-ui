package com.example.pricenotifierui.controller;

import com.example.pricenotifierui.entity.Task;
import com.example.pricenotifierui.entity.TaskStatus;
import com.example.pricenotifierui.entity.restclient.Message;
import com.example.pricenotifierui.entity.restclient.TaskUpdateDTO;
import com.example.pricenotifierui.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks/search")
    public ResponseEntity<Message> updateSearchTask(@Valid @RequestBody TaskUpdateDTO searchTaskUpdateDTO) {
        System.out.println("*** In update");
        Optional<Task> optionalTask = taskRepository.findByExternalId(searchTaskUpdateDTO.getId());
        if (optionalTask.isPresent()) {
            System.out.println("*** In isPresent");
            Task task = optionalTask.get();
            task.setTaskStatus(TaskStatus.valueOf(searchTaskUpdateDTO.getTaskStatus()));
            taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Success"));
        } else {
            System.out.println("*** In 404");
            return ResponseEntity.status(404).body(new Message("Task with id:" + searchTaskUpdateDTO.getId()
                    + " not found."));
        }
    }

    @GetMapping("/shootdown")
    public void shootdown() {
        ((ConfigurableApplicationContext) appContext).close();
    }

//    @GetMapping("/tasks/{taskId}")
//    public ResponseEntity<List<Product>> getTaskResults(@PathVariable("taskId") long taskId) {
//        List<Product> listProduct = priceNotifierService.getTaskResult(taskId);
//        return ResponseEntity.status(HttpStatus.OK).body(listProduct);
//    }

//    //    @GetMapping("/tasks/{taskId}")
//    public ResponseEntity<Task> getProcessedTask(@PathVariable("taskId") long taskId) {
//        Task task = priceNotifierService.getProcessedTask(taskId);
//        return ResponseEntity.status(HttpStatus.OK).body(task);
//    }

}
