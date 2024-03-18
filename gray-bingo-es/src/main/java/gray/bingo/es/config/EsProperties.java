package gray.bingo.es.config;

import java.security.SecureRandom;
import java.util.List;

/**
 * 描述：es 属性
 *
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID REASON   PERSON     DATE
 *  1 Create   高福兵(01409246)   2021/11/11
 * ****************************************************************************
 * </pre>
 *
 * @author 高福兵(01409246)
 * @version 1.0
 */
public class EsProperties {
    private String name;
    private String password;
    private String rule;
    private boolean openLog;
    private List<String> clusterNodes;


    public EsProperties(String name, String password, List<String> clusterNodes, String rule,boolean openLog) {
        this.name = name;
        this.password = password;
        this.rule = rule;
        this.clusterNodes = clusterNodes;
        this.openLog = openLog;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getClusterNodes() {
        return clusterNodes;
    }


    public boolean isOpenLog() {
        return openLog;
    }

    public void setOpenLog(boolean openLog) {
        this.openLog = openLog;
    }

    public void setClusterNodes(List<String> clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    /**
     * @return java.lang.String
     * @Author 高福兵(01409246)
     * @Description 获取随机Ip
     * @Date 2021/11/11
     * @Param []
     **/
    public String getServiceIp() {
        SecureRandom rand = new SecureRandom();
        int index = rand.nextInt(this.clusterNodes.size());
        return this.clusterNodes.get(index);
    }
}