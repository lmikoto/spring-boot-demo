package com.example.demo;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Process {

    private List<Map<String, Object>> data = new ArrayList<>();

    @Autowired
    private RuntimeService runtimeService;

    @Before
    public void init(){
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i + 1);
            map.put("username", "员工" + i);
            map.put("title", "请假" + i);
            map.put("day", i);
            data.add(map);
        }
    }

    @Test
    public void startProcess(){
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("AskLeave","1");
        System.out.println("id: " + instance.getId());
    }

    @Autowired
    private TaskService taskService;

    @Test
    public void querryTask(){

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("user1")
                // 分页查询
                // .listPage(firstResult, maxResults)
                // 排序
                // .orderByTaskCreateTime().desc()
                // 如果你知道这个查询是一条记录的话, 可以使用 .singleResult() 方法来获取单一的记录
                // .singleResult()
                .list();
        for (Task task : tasks) {
            System.out.println(task.toString()); // Task[id=2505, name=提交请假]
        }

    }

    @Test
    public void completeTask() {
        taskService.complete("25005");
    }
}
