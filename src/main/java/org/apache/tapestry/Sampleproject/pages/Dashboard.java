package org.apache.tapestry.Sampleproject.pages;

import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import com.example.model.User;
import com.example.service.Userservice;

public class Dashboard {
	
	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	 
	@InjectComponent("but")
	private Form but;
	 
	@Inject
	private AlertManager alertManager;
	
	private List<Object[]> data;
	public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }
    
    private Object[] row;

    public Object[] getRow() {
        return row;
    }

    public void setRow(Object[] row) {
        this.row = row;
    }
    @Property
    private String name;
	@Property
    private String welcomeMessage = "Welcome ";
	
	@Property
    private String email;
	
	@Inject
    private Userservice userService;
	
	User u=new User();
	String filepath;
	
    void onActivate(String name,String email) {
    	this.name=name;
        this.email=email;
        u=userService.getUserByEmail(email);
        if(u!=null) {
        data = new ArrayList<>();
        row = new Object[] {
        		u.getId(),
                u.getName(),
                u.getEmail(),
                u.getPassword(),
                u.getBdate(),
                u.getCnumber(),
                u.getUrl(),
            };
            data.add(row);
        } 
    }
    
    public Object getElementByIndex(int index) {
        if (row != null && index >= 0 && index < row.length) {
            return row[index];
        }
        return null; 
    }
    
    @InjectPage
    private Edit editDataPage;

    Object onActionFromEditLink(String email) {
    	Link targetPageLink = pageRenderLinkSource.createPageRenderLinkWithContext(Edit.class,email);
    	return targetPageLink;
    }
    
    Object onActionFromDeleteLink(String email) {
    	userService.DeleteUserByEmail(email);
    	return this; 
    }
   
    @OnEvent(value = "success", component = "but")
    Object onActionFromButtonLink() {
    	return Index.class; 
    }
 }
