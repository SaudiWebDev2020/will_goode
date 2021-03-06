package com.wgoode3.coffeeshop.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wgoode3.coffeeshop.models.Drink;
import com.wgoode3.coffeeshop.models.Shop;
import com.wgoode3.coffeeshop.services.DrinkService;
import com.wgoode3.coffeeshop.services.ShopService;

@Controller
public class HomeController {
	
	private ShopService shopServ;
	private DrinkService drinkServ;
	
	public HomeController(ShopService shopServ, DrinkService drinkServ) {
		this.shopServ = shopServ;
		this.drinkServ = drinkServ;
	}

    @GetMapping("/")
    public String index(Model model) {
    	model.addAttribute("shop", new Shop());
    	model.addAttribute("shops", shopServ.getAll());
        return "index.jsp";
    }
    
//    @PostMapping("/shop")
//    public String create(@RequestParam(value="name") String name, 
//    		@RequestParam(value="location") String location, 
//    		@RequestParam(value="description") String description) {
//    	System.out.println("name: " + name);
//    	System.out.println("location: " + location);
//    	System.out.println("description: " + description);
//    	return "redirect:/";
//    }
    
//    @PostMapping("/shop")
//    public String create(@RequestParam Map<String, String> formData) {
//    	System.out.println("name: " + formData.get("name"));
//    	System.out.println("location: " + formData.get("location"));
//    	System.out.println("description: " + formData.get("description"));
//    	return "redirect:/";
//    }
    
    @PostMapping("/shop")
    public String create(@Valid @ModelAttribute("shop") Shop shop, BindingResult result, 
    		HttpSession session, Model model) {
    	System.out.println(shop);
    	// This checks if the shop founded date is in the past
    	if(shop.getFounded().after(new Date())) {
    		result.rejectValue("founded", "time", "The Founded date must be in the past!");
    	}
    	if(result.hasErrors()) {
    		System.out.println(result.getAllErrors());
    		model.addAttribute("shops", shopServ.getAll());
    		return "index.jsp"; // if there are errors re-render the page
    	}
    	Integer count = (Integer) session.getAttribute("count");
    	if(count == null) {
    		count = 0;
    	}
    	session.setAttribute("count", count+1);
    	session.setAttribute("shop", shop);
    	shopServ.create(shop);
    	return "redirect:/shop"; // if the data is valid redirect
    }
    
    @GetMapping("/shop")
    public String showShop() {
    	return "shop.jsp";
    }
    
    @GetMapping("/shop/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("shop", shopServ.getOne(id));
    	model.addAttribute("newDrink", new Drink());
    	return "view.jsp";
    }
    
    @GetMapping("/shop/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("toEdit", shopServ.getOne(id));
    	return "edit.jsp";
    }
    
    @PostMapping("/shop/{id}/update")
    public String update(@Valid @ModelAttribute("toEdit") Shop toEdit, BindingResult result, 
    		@PathVariable("id") Long id, Model model) {
    	if(result.hasErrors()) {
    		return "edit.jsp";
    	}
    	shopServ.update(toEdit);
    	return "redirect:/shop/" + id;
    }
    
    @GetMapping("/shop/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
    	shopServ.remove(id);
    	return "redirect:/";
    }
    
    @PostMapping("/shop/{shop_id}/adddrink")
    public String addDrink(@Valid @ModelAttribute("newDrink") Drink newDrink, BindingResult result, 
    			@PathVariable("shop_id") Long shop_id, Model model) {
    	if(result.hasErrors()) {
    		model.addAttribute("shop", shopServ.getOne(shop_id));
    		return "view.jsp";
    	}
    	drinkServ.create(newDrink);
    	return "redirect:/shop/" + shop_id;
    }
    
    @GetMapping("/top3")
    public String top3(Model model) {
    	model.addAttribute("top3drinks", drinkServ.top3HealthyDrinks());
    	return "top3.jsp";
    }
    
    @GetMapping("/search")
    public String searchResults(@RequestParam("q") String q, Model model) {
    	model.addAttribute("top3drinks", drinkServ.searchByName(q));
    	return "top3.jsp";
    }

}