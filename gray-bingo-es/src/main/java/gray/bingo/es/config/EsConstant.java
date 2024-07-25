package gray.bingo.es.config;

import java.util.Arrays;
import java.util.List;

/**
 * 描述：常量类
 *
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID REASON   PERSON     DATE
 *  1 Create   高福兵(01409246)   2021/10/11
 * ****************************************************************************
 * </pre>
 *
 * @author 高福兵(01409246)
 * @version 1.0
 */
public class EsConstant {

    public final static String DEFAULT = "default";
    /**
     * es 多数据源注入不初始化字段
     **/
    public final static List<String> DATASOURCE_UNINITIALIZED = Arrays.asList(DEFAULT, "mapper");

    /**
     * 替换控制符的正则表达式
     **/
    public static final String CONTROL_CHAR_REGEX = "[\\x00-\\x1F]";

}