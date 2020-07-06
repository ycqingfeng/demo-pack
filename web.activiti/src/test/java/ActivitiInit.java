import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiInit {

    @Test
    public void init(){
        ProcessEngineConfiguration conf = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();

        conf.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        conf.setJdbcUrl("jdbc:mysql://ycsin.cn:3306/activiti?serverTimezone=UTC&characterEncoding=utf8&useSSL=false");
        conf.setJdbcUsername("root");
        conf.setJdbcPassword("PassW0rd");
        conf.setDatabaseSchemaUpdate("true");
        ProcessEngine engine = conf.buildProcessEngine();
    }
}
