package org.ibas.credit.util;

import org.ibas.credit.accept.service.PolicyService;
import org.ibas.framework.core.service.ServiceException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanFactoryUtils implements ApplicationContextAware {

    private static ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");

    /**
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     * 
     * @param applicationContext
     *            ApplicationContext 对象.
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }

    /**
     * 获取ApplicationContext
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return appCtx;
    }

    /**
     * 这是一个便利的方法，帮助我们快速得到一个BEAN
     * 
     * @param beanName
     *            bean的名字
     * @return 返回一个bean对象
     */
    public static Object getBean(String beanName) {
        return appCtx.getBean(beanName);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        PolicyService is = (PolicyService) ac.getBean("policyService");
        try {
            is.getHistoryPolicy("8a8291924a296928014a296c6a070010", "6");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

}
