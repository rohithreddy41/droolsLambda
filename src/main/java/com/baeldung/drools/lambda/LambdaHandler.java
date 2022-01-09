package com.baeldung.drools.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.baeldung.drools.config.DroolsBeanFactory;
import com.baeldung.drools.model.Applicant;
import com.baeldung.drools.model.SuggestedRole;
import org.kie.api.runtime.KieSession;


import java.io.IOException;
import java.util.List;
import java.util.Map;


public class LambdaHandler implements RequestHandler<Applicant, String> {
    @Override
    public String handleRequest(Applicant applicant, Context context)
    {
        LambdaLogger logger = context.getLogger();
        // log details
        logger.log("Name :  " + applicant.getName());
        logger.log("Age :  " + applicant.getAge());
        logger.log("Salary :  " + applicant.getCurrentSalary());

        KieSession kieSession=new DroolsBeanFactory().getKieSession();
        SuggestedRole suggestedRole = new SuggestedRole();
        logger.log("Suggested role before :  " + suggestedRole.getRole());
        kieSession.insert(applicant);
        kieSession.setGlobal("suggestedRole",suggestedRole);
        kieSession.fireAllRules();
        logger.log("Suggested role after :  " + suggestedRole.getRole());

        return "success";
    }
}
