package com.realdomen.springmvc.controllers;

import com.realdomen.springmvc.models.Employee;
import com.realdomen.springmvc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employee") // this will create a request url in the form of localhost:port/employee
public class EmployeeController {
    /**
     * Autowired means that spring will inject an implementation of the interface EmployeeDAO
     * WHICH IS A SINGLETON OBJECT BTW ! (aka beans).
     * so it will search for the implementation class that is annotated with @Service
     * WARNING ! if 2 implementation classes implements the same interface,
     * it will throw a NoUniqueBeanDefinitionException.
     * With @Qualifier you can make the spring beans more unique, so it will know which bean to autowire.
     * Don't forget to also put @Qualifier on the implementation classes !
     */
    @Qualifier("empService")
    @Autowired
    EmployeeService employeeService;

    @Qualifier("hrService")
    @Autowired
    EmployeeService hrService;

    /**
     * @RequestMapping or @GetMapping will create a request url in the form of localhost:port/employee/getAllEmployees
     * With  @RequestMapping you also need to define which http method it needs to use GET or POST. But it's not needed with @GetMapping
     * GET is usually used to let the browser GET information, in our case it's the view and model.
     * POST means that the browser will send some information (see on https://en.wikipedia.org/wiki/POST_(HTTP) for more information)
     */
    @RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
    public ModelAndView getAllEmployees(ModelMap modelMap) { // ModelMap is like a Hashmap spring will automatically initialize this for you.
        modelMap.addAttribute("employees", employeeService.getAllEmployees()); // key: employees value: an employee arraylist

        /* the first parameter of the ModelAndView constructor must be the exact name of your view so employeeListDisplay, which will fetch employeeListDisplay.html
         * the second parameter is your modelMap which contains all the data that it needs to fill in our html page
         * */
        return new ModelAndView("employeeListDisplay", modelMap);
    }

    @GetMapping("/add")
    public ModelAndView showAddView(ModelMap modelMap) {
        modelMap.addAttribute("emp",new Employee());
        modelMap.addAttribute("professions", employeeService.getProfessions());
        return new ModelAndView("addEmployee", modelMap);
    }

    /**
     * the browser could request a url in the form of localhost:port/employee/5/edit ,
     * Again this will only send the view (html page) and the model!
     * To fetch the id part of the url in our method we need to use @PathVariable("id")
     * be aware that "id" between () must be the same as the {id} in the url part.
     */
    @GetMapping("/{id}/edit") // <---- Creates url in the form of localhost:port/employee/{id}/edit
    public ModelAndView showEditPage(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("employee", employeeService.findById(id));
        modelMap.addAttribute("professions", employeeService.getProfessions());
        return new ModelAndView("editEmployee", modelMap);
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        employeeService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", true); // this is used to show the toast or the alert bar in our page
        /**
         * If you don't want to send the view but just want the browser redirect to another URL.
         * You can prefix the url with redirect: which will go to the url localhost:????/employee/getAllEmployees
         */
        return new ModelAndView("redirect:/employee/getAllEmployees");
    }

    //  * POST means that the browser will send some information (see on https://en.wikipedia.org/wiki/POST_(HTTP) for more information)
    // so @ModelAttribute is the object that would be sended by our browser
    @PostMapping("/edit")
    public ModelAndView save(@ModelAttribute Employee employee) {
        employeeService.update(employee);
        return new ModelAndView("redirect:/employee/getAllEmployees");
    }

    @PostMapping("/add")
    public ModelAndView addEmployee(@ModelAttribute Employee employee) {
        employeeService.addEmployee(employee);
        return new ModelAndView("redirect:/employee/getAllEmployees");
    }
}
