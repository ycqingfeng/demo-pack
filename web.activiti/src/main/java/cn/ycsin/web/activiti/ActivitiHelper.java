package cn.ycsin.web.activiti;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ActivitiHelper {

    /*任务节点*/
    public static UserTask createUserTask(String id, String name, String candidateGroup){
        List<String> candidateGroups = new ArrayList<String>();
        candidateGroups.add(candidateGroup);
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateGroups(candidateGroups);
        return userTask;
    }

    /*连线*/
    protected static SequenceFlow createSequenceFlow(String from, String to, String name, String conditionExpression){
        SequenceFlow sf = new SequenceFlow();
        sf.setSourceRef(from);
        sf.setTargetRef(to);
        sf.setName(name);
        if(!StringUtils.isEmpty(conditionExpression)){
            sf.setConditionExpression(conditionExpression);
        }

        return sf;
    }

    /*排他网关*/
    protected static ExclusiveGateway createExclusiveGateway(String id){
        ExclusiveGateway eg = new ExclusiveGateway();
        eg.setId(id);
        return eg;
    }


    /*开始节点*/
    protected static StartEvent createStartEvent(String id) {
        StartEvent se = new StartEvent();
        se.setId("startEvent");
        return se;
    }

    /*结束节点*/
    protected static EndEvent createEndEvent() {
        EndEvent ev = new EndEvent();
        ev.setId("endEvent");
        return ev;
    }


    protected static ProcessEngine getProcessEngine(){
        ProcessEngine processEngine=ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
                .setJdbcUrl("jdbc:mysql://ycsin.cn:3306/activiti?serverTimezone=UTC&characterEncoding=utf8&useSSL=false")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("PassW0rd")
                .setDatabaseSchemaUpdate("true")
//                .setJobExecutorActivate(false)
                .buildProcessEngine();
        return processEngine;
    }


    public static void main(String[] args) throws IOException {
        System.out.println(".........start...");
        ProcessEngine processEngine=getProcessEngine();

        // 1. Build up the model from scratch
        BpmnModel model = new BpmnModel();
        Process process=new Process();
        model.addProcess(process);
        final String PROCESSID ="process01";
        final String PROCESSNAME ="测试01";
        process.setId(PROCESSID);
        process.setName(PROCESSNAME);

        process.addFlowElement(createStartEvent(""));
        process.addFlowElement(createUserTask("task1", "节点01", "candidateGroup1"));
        process.addFlowElement(createExclusiveGateway("createExclusiveGateway1"));
        process.addFlowElement(createUserTask("task2", "节点02", "candidateGroup2"));
        process.addFlowElement(createExclusiveGateway("createExclusiveGateway2"));
        process.addFlowElement(createUserTask("task3", "节点03", "candidateGroup3"));
        process.addFlowElement(createExclusiveGateway("createExclusiveGateway3"));
        process.addFlowElement(createUserTask("task4", "节点04", "candidateGroup4"));
        process.addFlowElement(createEndEvent());

        process.addFlowElement(createSequenceFlow("startEvent", "task1", "", ""));
        process.addFlowElement(createSequenceFlow("task1", "task2", "", ""));
        process.addFlowElement(createSequenceFlow("task2", "createExclusiveGateway1", "", ""));
        process.addFlowElement(createSequenceFlow("createExclusiveGateway1", "task1", "不通过", "${pass=='2'}"));
        process.addFlowElement(createSequenceFlow("createExclusiveGateway1", "task3", "通过", "${pass=='1'}"));
        process.addFlowElement(createSequenceFlow("task3", "createExclusiveGateway2", "", ""));
        process.addFlowElement(createSequenceFlow("createExclusiveGateway2", "task2", "不通过", "${pass=='2'}"));
        process.addFlowElement(createSequenceFlow("createExclusiveGateway2", "task4", "通过", "${pass=='1'}"));
        process.addFlowElement(createSequenceFlow("task4", "createExclusiveGateway3", "", ""));
        process.addFlowElement(createSequenceFlow("createExclusiveGateway3", "task3", "不通过", "${pass=='2'}"));
        process.addFlowElement(createSequenceFlow("createExclusiveGateway3", "endEvent", "通过", "${pass=='1'}"));

        // 2. Generate graphical information
//        new BpmnAutoLayout(model).execute();

        // 3. Deploy the process to the engine
        Deployment deployment = processEngine.getRepositoryService().createDeployment().addBpmnModel(PROCESSID+".bpmn", model).name(PROCESSID+"_deployment").deploy();

        // 4. Start a process instance
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(PROCESSID);

        // 5. Check if task is available
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
//        Assert.equa(1, tasks.size());

        // 6. Save process diagram to a file
//        InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
//        FileUtils.copyInputStreamToFile(processDiagram, new File("I:\\dev_java2019\\demos\\spring_cloud\\web.activiti\\src\\main\\java\\cn\\ycsin\\web\\activiti\\images\\"+PROCESSID+".png"));
        // 7. Save resulting BPMN xml to a file
        InputStream processBpmn = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), PROCESSID+".bpmn");
        FileUtils.copyInputStreamToFile(processBpmn,new File("I:\\dev_java2019\\demos\\spring_cloud\\web.activiti\\src\\main\\java\\cn\\ycsin\\web\\activiti\\images\\"+PROCESSID+".bpmn"));

        System.out.println(".........end...");
    }
}
