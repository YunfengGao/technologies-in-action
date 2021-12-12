package com.github.yunfeng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ParseException {
        ProcessEngine processEngine = getProcessEngine();
        ProcessDefinition processDefinition = getProcessDefinition(processEngine);
        ProcessInstance processInstance = getProcessInstance(processEngine, processDefinition);
        processTask(processEngine, processInstance);
    }

    private static ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = cfg.buildProcessEngine(); // 创建流程
        String name = processEngine.getName();
        String version = ProcessEngine.VERSION;
        LOGGER.info("流程引擎的名称[{}]，流程引擎的版本[{}]", name, version);
        return processEngine;
    }

    private static ProcessDefinition getProcessDefinition(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("blue_green_deploy.bpmn20.xml");
        Deployment deployment = deploymentBuilder.deploy();
        String deploymentId = deployment.getId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                               .deploymentId(deploymentId)
                                                               .singleResult();
        LOGGER.info("流程定义文件[{}]，流程ID[{}]", processDefinition.getName(), processDefinition.getId());
        return processDefinition;
    }

    private static ProcessInstance getProcessInstance(ProcessEngine processEngine,
                                                      ProcessDefinition processDefinition) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        LOGGER.info("启动流程[{}]", processInstance.getProcessDefinitionKey());
        return processInstance;
    }

    private static void processTask(ProcessEngine processEngine,
                                    ProcessInstance processInstance) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        while (processInstance != null && !processInstance.isEnded()) {
            // 处理流程任务
            TaskService taskService = processEngine.getTaskService();
            List<Task> list = taskService.createTaskQuery().list();
            LOGGER.info("待处理任务量[{}]", list.size());
            for (Task task : list) {
                LOGGER.info("待处理的任务[{}]", task.getName());
                FormService formService = processEngine.getFormService();
                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                List<FormProperty> formProperties = taskFormData.getFormProperties();
                HashMap<String, Object> variables = new HashMap<>();
                String line = null;
                for (FormProperty formProperty : formProperties) {
                    if (formProperty.getType() instanceof StringFormType) {
                        LOGGER.info("请输入[{}]？", formProperty.getName());
                        line = scanner.nextLine();
                        variables.put(formProperty.getId(), line);
                    } else if (formProperty.getType() instanceof DateFormType) {
                        LOGGER.info("请输入[{}]？格式（yyyy-MM-dd）", formProperty.getName());
                        line = scanner.nextLine();
                        SimpleDateFormat dateFormType = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = dateFormType.parse(line);
                        variables.put(formProperty.getId(), date);
                    } else {
                        LOGGER.info("暂时不支持该内容[{}]", formProperty.getType());
                    }
                    LOGGER.info("您输入的类容是[{}]", line);
                }
                taskService.complete(task.getId(), variables);
                processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                                               .processInstanceId(processInstance.getId()).singleResult();
            }
        }
        scanner.close();
    }
}