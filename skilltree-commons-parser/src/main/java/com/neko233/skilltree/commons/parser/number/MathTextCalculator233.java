package com.neko233.skilltree.commons.parser.number;

import com.alibaba.fastjson2.JSON;
import com.neko233.skilltree.commons.core.base.KvTemplate233;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数学文本计算器公式解析引擎
 *
 * @author SolarisNeko
 */
@Slf4j
public class MathTextCalculator233 {

    // --------------- constant -------------------

    /**
     * 计算中保留小数位数
     */
    public static final int CAL_SCALE_SIZE = 8;

    /**
     * 计算中保留小数位数 处理原则
     */
    public static final int CAL_SCALE_ROUND = BigDecimal.ROUND_HALF_DOWN;

// --------------- /constant -------------------


    /**
     * 表达式字符 - 合法性校验正则模式 (要包含 number + opt)
     */
    private static final String EXPRESSION_PATTERN_REGEX = "[0-9\\.\\+\\-\\*\\/\\\\(\\)_%Ee\\s\\^\\`]+";

    /**
     * 判断数字
     */
    private static final String NUM_REGEX = "\\d+(\\.)?\\d*((E|e|E\\+|e\\+|E-|e-)\\d+)?";

    /**
     * 操作符号 opt 提取 (如果 opt 较长, need priority)
     * ((|)|+|-|*|/|_|%|^)
     */
    private static final String ADD_SPACE_REGEX = "((\\d+.?\\d*[Ee]([+\\-])?\\d+)" +
            "|(\\/\\-)" +
            "|([()+\\-*/_%])" +
            "|(\\^)" +
            "|(\\`))";

    /**
     * 空格
     */
    private static final String SPACE = "\\s";


    public static BigDecimal executeExpressionByTemplate(String calExpressionTemplate, Map<String, ? extends Number> kvMap) throws Exception {
        return executeExpressionByTemplate(calExpressionTemplate, kvMap, 2);
    }

    public static BigDecimal executeExpressionByTemplate(String calExpressionTemplate, Map<String, ? extends Number> kvMap, int scaleSize) throws Exception {
        String calExpression = KvTemplate233.builder(calExpressionTemplate)
                .put(kvMap)
                .build();
        return executeExpression(calExpression, scaleSize, BigDecimal.ROUND_HALF_DOWN);
    }

    public static BigDecimal executeExpression(String calExpressionStr, int scaleSize) throws Exception {
        return executeExpression(calExpressionStr, scaleSize, BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 保留小数
     *
     * @param calExpressionStr 数学公式文本
     * @param scaleSize        消暑精度
     * @param bigRoundType     取整方式
     * @return 高精度数值
     * @throws Exception
     */
    public static BigDecimal executeExpression(String calExpressionStr, int scaleSize, int bigRoundType) throws Exception {
        return calculate(calExpressionStr).setScale(scaleSize, bigRoundType);
    }

    /**
     * 计算表达式
     *
     * @param calExpressionText 数学公式文本
     * @return 计算结果
     * @throws Exception 任意异常
     */
    public static BigDecimal executeExpression(String calExpressionText) throws Exception {
        return executeExpression(calExpressionText, 2);
    }

    /**
     * 计算 中序 字符串
     *
     * @param calExpressionStr 计算表达式
     * @return 计算结果
     */
    public static BigDecimal calculate(String calExpressionStr) throws Exception {
        calExpressionStr = check2RepairExpression(calExpressionStr);

        List<String> inorderExpressionList = getInorderExpressionList(calExpressionStr);

        // 生成 逆波兰 表达式 list
        List<String> suffixExpressionList = getSuffixExpressionList(inorderExpressionList);

        if (log.isDebugEnabled()) {
            log.info("中序表达式：{}", JSON.toJSONString(inorderExpressionList));
            log.info("后缀表达式：{}", JSON.toJSONString(suffixExpressionList));
        }

        return calculate(suffixExpressionList);
    }

    /**
     * 生成中序表达式 list
     *
     * @param calExpressionStr 表达式
     * @return 中缀表达式 token List
     */
    private static List<String> getInorderExpressionList(String calExpressionStr) {
        // 生成 中序表达式 list
        return Arrays.stream(calExpressionStr.split(SPACE))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }

    /**
     * 计算 逆波兰 / 后序表达式 List
     *
     * @param suffixExpressionList 后缀表达式
     * @return 计算结果
     */
    private static BigDecimal calculate(List<String> suffixExpressionList) throws Exception {
        if (CollectionUtils.isEmpty(suffixExpressionList)) {
            return BigDecimal.ZERO;
        }

        Stack<BigDecimal> numStack = new Stack<>();

        for (String op2num : suffixExpressionList) {
            if (StringUtils.isBlank(op2num)) {
                continue;
            }
            // 使用正则表达式取出
            if (isNumber(op2num)) {
                // 数字
                numStack.push(new BigDecimal(op2num));
            } else {
                // 运算符
                OptEnum opEnum = OptEnum.getEnum(op2num);
                if (opEnum.getCalFunction() == null) {
                    throw new RuntimeException("操作符不支持！");
                }

                BigDecimal bigDecimal2 = numStack.pop();
                BigDecimal bigDecimal1 = numStack.pop();

                BigDecimal resultBigDecimal = opEnum.getCalFunction().apply(bigDecimal1, bigDecimal2);

                // 结果入栈
                numStack.push(resultBigDecimal);
            }
        }

        return numStack.pop();
    }

    /**
     * 生成逆波兰 / 后续表达式 list
     *
     * @param inorderExpressionList
     * @return
     */
    private static List<String> getSuffixExpressionList(final List<String> inorderExpressionList) {
        Stack<String> opStack = new Stack<>();

        // 不通过中间栈，在进行逆序处理，直接输出到list中，就是需要的逆波兰表达式
        List<String> resList = new ArrayList<>(inorderExpressionList.size());
        for (String op2num : inorderExpressionList) {
            // 数字
            if (isNumber(op2num)) {
                resList.add(op2num);
                continue;
            }
            // (
            if (Objects.equals(op2num, OptEnum.OP_LEFT_BRACKET.getOpt())) {
                opStack.push(op2num);
                continue;
            }
            // )
            if (Objects.equals(op2num, OptEnum.OP_RIGHT_BRACKET.getOpt())) {
                while (!opStack.peek().equals(OptEnum.OP_LEFT_BRACKET.getOpt())) {
                    resList.add(opStack.pop());
                }
                // 去掉 (
                opStack.pop();
                continue;
            }


            // 操作符 优先级
            while (!CollectionUtils.isEmpty(opStack)
                    && OptEnum.getEnum(opStack.peek()).getOptPriority() >= OptEnum.getEnum(op2num).getOptPriority()) {
                resList.add(opStack.pop());
            }
            // 将 操作符 最后加入
            opStack.push(op2num);
        }

        // 处理剩余的操作符
        while (!CollectionUtils.isEmpty(opStack)) {
            resList.add(opStack.pop());
        }

        return resList;
    }

    /**
     * 判断是否数学表达式
     *
     * @param mathStr 数学表达式
     * @return
     */
    public static boolean isNumber(String mathStr) {
        if (mathStr == null) {
            return false;
        }
        return mathStr.matches(NUM_REGEX);
    }


    /**
     * 校验 & 整理字符串
     * 科学计数处理
     *
     * @param calExpressionStr
     * @return
     */
    private static String check2RepairExpression(String calExpressionStr) {
        // 非空校验
        if (StringUtils.isBlank(calExpressionStr)) {
            throw new IllegalArgumentException("表达式不能为空！");
        }

        // 表达式字符合法性校验
        if (!calExpressionStr.matches(EXPRESSION_PATTERN_REGEX)) {
            throw new IllegalArgumentException("表达式含有非法字符！" + calExpressionStr);
        }

        // 整理字符串
        calExpressionStr = calExpressionStr.replaceAll(SPACE, "");

        // (- 替换为 (0-
        calExpressionStr = calExpressionStr.replace(
                OptEnum.OP_LEFT_BRACKET.getOpt() + OptEnum.OP_SUB.getOpt()
                , OptEnum.OP_LEFT_BRACKET.getOpt() + BigDecimal.ZERO + OptEnum.OP_SUB.getOpt());

        // - 开始 前缀 0-
        if (calExpressionStr.startsWith(OptEnum.OP_SUB.getOpt())) {
            calExpressionStr = BigDecimal.ZERO + calExpressionStr;
        }

        calExpressionStr = calExpressionStr.replaceAll(ADD_SPACE_REGEX, " $1 ").trim();

        if (log.isDebugEnabled()) {
            log.info("整理后的运算串：{}", calExpressionStr);
        }

        return calExpressionStr;
    }


}