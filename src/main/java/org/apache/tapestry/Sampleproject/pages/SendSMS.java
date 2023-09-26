package org.apache.tapestry.Sampleproject.pages;
import org.apache.tapestry5.annotations.OnEvent;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class SendSMS {
	
	
    @Property
    private String phoneNumber;

    @Property
    private String message;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
    
	@OnEvent(value = "success", component = "smsForm")
    public Object onSuccess() {
        // Replace this with actual SMS API integration later.
        // For now, simulate sending the SMS and print to the console.
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);

        // Redirect to a confirmation page or stay on the same page.
        return pageRenderLinkSource.createPageRenderLinkWithContext(Dashboard.class);
    }
}


