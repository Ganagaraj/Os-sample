package com.alstom.openschedule.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.alstom.openschedule.model.Customer;
import com.alstom.openschedule.repository.CustomerRepository;




@SuppressWarnings("deprecation")
@ManagedBean(name = "customerService", eager = true)
@SessionScoped
@RequestScoped
public class Customercontroller<T> {
	
	
	private CustomerRepository cusRep = new CustomerRepository();
	private DataModel items = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataModel findAll() {
		List <T> cus = cusRep.getCustomers();
		return  new ListDataModel(cus);
	}

	public DataModel getItems() {
		 if(items==null) {
			 items = findAll();
		 }
		 return items;
	}
  

    private void recreateModel() {
        items = null;
    }
    
	public String create() {
		Customer cusObj = new Customer();
		cusObj.setCreating(true);
		cusObj.setEditing(true);
	    List<Customer> oldArray = (List<Customer>) getItems().getWrappedData();
        oldArray.add(0, cusObj);
		getItems().setWrappedData(oldArray);
		return null;

	}


	public String delete(Customer customer) {
          
		if(!customer.isCreating()) {
			 cusRep.deleteCustomer(customer);
			  System.out.println("DELETE BEING CALLED");
		}
		 
		  recreateModel();
		  return "List";

	}
	
	public String cancel(Customer customer) {
		 if(customer.isCreating() || customer.isEditing()) {
			 if(customer.isEditing()) {
				 customer.setEditing(false);
			 }
			 recreateModel();
		 }
		 return "List";
	}
	
	  public String activateEdit(Customer customer) {
	        customer.setEditing(true);
	        return null;
	    }
	public String save(Customer customer) {
		
		if(customer.isCreating()) {
			System.err.println("Creatting");
			System.out.println(customer.getName() + customer.isCreating());
		 
		}
		System.out.println(customer.getName() + customer.isEditing());
		customer.setEditing(false);
		return null;
	}
	
	

}
