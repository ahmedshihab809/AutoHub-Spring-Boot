package com.mycompany.Controller;

import com.mycompany.Model.*;
import com.mycompany.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired private UserService userService;
    @Autowired private WorkshopService workshopService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EmailService emailService;
    @Autowired private VerifyService verifyService;
    @Autowired private SessionService sessionService;
    @Autowired private PartService partService;
    @Autowired private ServicingService servicingService;
    @Autowired private HttpServletRequest request;
    public MainController(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }
    @GetMapping("/")
    public String showHomePage(){
        return "home";
    }
    @GetMapping("/user_signup")
    public String user_signup(Model model){
        model.addAttribute("user",new User());
        return "user_signup";
    }
    @PostMapping("/user_signup")
    public String user_signup_post(@RequestParam("confirmPassword") String confirmPassword
            , User user, RedirectAttributes ra){
        if(!confirmPassword.equals(user.getPassword())){
            ra.addFlashAttribute("message","Passwords Dont Match!");
            return "redirect:/user_signup";
        }
        boolean flag=userService.save(user);
        if(flag){
            ra.addFlashAttribute("message","The User Has been Registered Successfully");
            return "redirect:/";
        }
        else{
            ra.addFlashAttribute("message","Email already exists!");
            return "redirect:/user_signup";
        }
    }
    @GetMapping("/workshop_signup")
    public String workshop_signup(Model model){
        model.addAttribute("workshop",new Workshop());
        return "workshop_signup";
    }
    @PostMapping("/workshop_signup")
    public String workshop_signup_post(@RequestParam("confirmPassword") String confirmPassword
            , Workshop workshop, RedirectAttributes ra){

        if(!confirmPassword.equals(workshop.getPassword())){
            ra.addFlashAttribute("message","Passwords Dont Match!");
            return "redirect:/workshop_signup";
        }
        boolean flag= workshopService.save(workshop);
        if(flag){
            ra.addFlashAttribute("message","The Workshop Has been Registered Successfully");
            return "redirect:/";
        }
        else{
            ra.addFlashAttribute("message","Email already exists!");
            return "redirect:/workshop_signup";
        }
    }

    @PostMapping("/login")
    public String login(/*HttpServletRequest request,*/RedirectAttributes ra,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam("selectBox") String userType){

        HttpSession session;

        if(userType.equals("car_owner")){
            User tempUser= userService.login(email, password);
            if(tempUser==null){
                ra.addFlashAttribute("failure","Email does not Exist!");
                return "redirect:/";
            }
            else{
                String db_password=tempUser.getPassword();
                if(!tempUser.isVerified()){
                    ra.addFlashAttribute("failure","Please Verify Your Email First");
                    return "redirect:/";
                }
                else if(passwordEncoder.matches(password,db_password)){
                    session=request.getSession();
                    String username=tempUser.getFirstName()+tempUser.getLastName();
                    session.setAttribute("username",username);
                    session.setAttribute("email",tempUser.getEmail());
                    session.setAttribute("mobile",tempUser.getMobile());
                    ra.addFlashAttribute("message","Login Done Successfully");
                    return "redirect:/userProfile";
                }
                else{
                    ra.addFlashAttribute("failure","Password Incorrect!");
                    return "redirect:/";
                }
            }
        }
        else{
            Workshop tempWorkshop= workshopService.login(email, password);
            if(tempWorkshop==null){
                ra.addFlashAttribute("failure","Email does not Exist!");
                return "redirect:/";
            }
            else{
                String db_password=tempWorkshop.getPassword();
                if(!tempWorkshop.isVerified()){
                    ra.addFlashAttribute("failure","Please Verify Your Email first!");
                    return "redirect:/";
                }
                else if(passwordEncoder.matches(password,db_password)){
                    session=request.getSession();
                    session.setAttribute("username",tempWorkshop.getName());
                    session.setAttribute("email",tempWorkshop.getEmail());
                    session.setAttribute("mobile",tempWorkshop.getMobile());
                    ra.addFlashAttribute("message","Login Done Successfully");
                    return "redirect:/workshopProfile";
                }
                else{
                    ra.addFlashAttribute("failure","Password is Incorrect!");
                    return "redirect:/";
                }
            }
        }
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes ra){
        HttpSession session= request.getSession();
        sessionService.invalidate(session);
        ra.addFlashAttribute("message","Logout Successful");
        return "redirect:/";
    }
    @GetMapping("/userProfile")
    public String userProfile(Model model,RedirectAttributes ra){
        HttpSession session=request.getSession();
        if(sessionService.isValid(session)) {
            String email = (String) session.getAttribute("email");
            User user = userService.findByEmail(email);
            List<Appointment> appointment=userService.findAppointments(user.getId());
            model.addAttribute("appointments",appointment);
            return "user_profile";
        }
        else{
            ra.addFlashAttribute("failure","Session has ended. Please Login Again!");
            return "redirect:/";
        }
    }
    @GetMapping("/workshopProfile")
    public String workshopProfile(Model model,RedirectAttributes ra){
        HttpSession session=request.getSession();
        if(sessionService.isValid(session)) {
            String email = (String) session.getAttribute("email");
            Workshop workshop = workshopService.get(email);
            List<Appointment> appointment=workshopService.findAppointments(workshop.getId());
            model.addAttribute("appointments",appointment);
            return "workshop_profile";
        }
        else{
            ra.addFlashAttribute("failure","Session has ended. Please Login Again!");
            return "redirect:/";
        }
    }

    @PostMapping("/completeAppointment")
    public String completeAppointment(@RequestParam("appointment_id") String id,
                                      RedirectAttributes ra){

        Integer appointmentId = Integer.parseInt(id);
        Appointment appointment = workshopService.findAppointmentById(appointmentId);
        workshopService.deleteAppointment(appointment);
        ra.addFlashAttribute("success","Appointment Completed");
        return "redirect:/workshopProfile";
    }

    @GetMapping("/nearestWorkshop")
    public String nearestWorkshop(Model model){
        HttpSession session = request.getSession();
        double userLat = (double) session.getAttribute("lat");
        double userLon = (double) session.getAttribute("lon");
        System.out.println(userLat);
        System.out.println(userLon);
        List<Workshop> workshops = workshopService.allWorkshops();
        double max = 1000000000.0000000;
        Workshop nearest = new Workshop();
        for(int i=0;i<workshops.size();i++){

            double workshopLat = workshops.get(i).getLatitude();
            double workshopLon = workshops.get(i).getLongitude();

            double latDistance = Math.toRadians(workshopLat - userLat);
            double lonDistance = Math.toRadians(workshopLon - userLon);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(workshopLat))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = 6371 * c;

            if(distance<max){
                nearest=workshops.get(i);
            }

        }
        model.addAttribute("nearest",nearest);
        return "getNearestWorkshop";
    }
    @GetMapping("/verification")
    public String verification(Model model){
        return "verification";
    }
    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam("email") String email,
                          @RequestParam("selectBox") String selectBox,
                          RedirectAttributes ra){
        if(selectBox.equals("car_owner")){
            User user = userService.get(email);
            if(user==null){
                ra.addFlashAttribute("failure","Email not found!");
                return "redirect:/";
            }
            else{
                if(user.isVerified()){
                    ra.addFlashAttribute("message","User already verified");
                    return "redirect:/";
                }
            }
        }
        else{
            Workshop workshop = workshopService.get(email);
            if(workshop==null){
                ra.addFlashAttribute("failure","Email not found!");
                return "redirect:/";
            }
            else{
                if(workshop.isVerified()){
                    ra.addFlashAttribute("message","Workshop already verified");
                    return "redirect:/";
                }
            }
        }
        String otp = emailService.sendEmail(email);
        ra.addFlashAttribute("message1","OTP has been sent");
        ra.addFlashAttribute("sent_otp",otp);
        ra.addFlashAttribute("hidden_email",email);
        ra.addFlashAttribute("selectBox",selectBox);
        return "redirect:/verification";
    }
    @PostMapping("/verification")
    public String verifyOTP(@RequestParam("otp") String otp,
                            @RequestParam("hidden_otp") String hidden_otp,
                            @RequestParam("hidden_email") String hidden_email,
                            @RequestParam("hidden_box") String selectBox,
                            RedirectAttributes ra){

        if(otp.equals(hidden_otp)){
            if(selectBox.equals("car_owner")) {
                verifyService.verifyUser(hidden_email);
            }
            else{
                verifyService.verifyWorkshop(hidden_email);
            }
            ra.addFlashAttribute("message","Verification Successful");
        }
        else{
            ra.addFlashAttribute("failure","Verification Unsuccessful");
        }
        return "redirect:/";
    }

    @GetMapping("/addParts")
    public String addParts(Model model){
        List<Part> parts=partService.allParts();
        model.addAttribute("parts",parts);
        return "add_parts";
    }
    @PostMapping("/addParts")
    public String addPartsPost(@RequestParam("selectedPart") String selectedPart,
                               @RequestParam("quantity") Integer quantity,
                               @RequestParam("manufacturer") String manufacturer,
                               @RequestParam("details") String details,
                               @RequestParam("price") Integer price,
                               RedirectAttributes ra){

//        System.out.println("Selected Part is: " + selectedPart);;
//        System.out.println(selectedPart.getClass().getSimpleName());
        HttpSession session= request.getSession();
        String email= (String) session.getAttribute("email");
        Integer part_id=Integer.parseInt(selectedPart);
        wPart temp = new wPart();
        temp.setDetails(details);
        temp.setPrice(price);
        temp.setManufacturer(manufacturer);
        temp.setQuantity(quantity);
        temp.setPart(partService.findPart(part_id));
        temp.setWorkshop(workshopService.get(email));
        partService.save(temp);

        ra.addFlashAttribute("success","Part Added Successfully");
        return "redirect:/workshopProfile";
    }
    @GetMapping("/addServices")
    public String addServices(Model model){
        List<Servicing> services=servicingService.allServices();
        model.addAttribute("services",services);
        return "add_services";
    }
    @PostMapping("/addServices")
    public String addServicesPost(@RequestParam("selectedService") String selectedService,
                               @RequestParam("details") String details,
                               @RequestParam("price") Integer price,
                               RedirectAttributes ra){

        HttpSession session= request.getSession();
        String email= (String) session.getAttribute("email");
        Integer service_id=Integer.parseInt(selectedService);
        wService temp = new wService();
        temp.setDetails(details);
        temp.setPrice(price);
        temp.setService(servicingService.findService(service_id));
        temp.setWorkshop(workshopService.get(email));
        servicingService.save(temp);

        ra.addFlashAttribute("success","Service Added Successfully");
        return "redirect:/workshopProfile";
    }

    @GetMapping("/deletePart")
    public String deletePart(Model model){
        HttpSession session =request.getSession();
        String email= (String) session.getAttribute("email");
        Workshop workshop=workshopService.get(email);
        List<wPart> parts = partService.findByWorkshop(workshop.getId());
        model.addAttribute("parts",parts);
        return "delete_parts";
    }
    @PostMapping("/deletePart")
    public String deletePart(@RequestParam("selectedPart") String selectedPart,
                             RedirectAttributes ra){
        Integer part_id = Integer.parseInt(selectedPart);
        partService.deleteById(part_id);

        ra.addFlashAttribute("success","Part deleted Successfully");
        return "redirect:/workshopProfile";
    }

    @GetMapping("/showAll")
    public String showAll(Model model){
        HttpSession session=request.getSession();
        String email= (String) session.getAttribute("email");
        Workshop workshop = workshopService.get(email);

        List<wService> services=servicingService.findByWorkshop(workshop.getId());
        List<wPart> parts=partService.findByWorkshop(workshop.getId());

        model.addAttribute("services",services);
        model.addAttribute("parts",parts);
        return "showAll";

    }

    @GetMapping("/searchService")
    public String searchService(){
        return "search_service";
    }
    @GetMapping("/searchPart")
    public String searchPart(){
        return "search_part";
    }
    @PostMapping("/searchPart")
    public String searchPartPost(@RequestParam("partName") String partName,
                                 Model model,
                                 RedirectAttributes ra){

    Part part = partService.findByName(partName);
    Integer part_id=part.getId();
    List<wPart> parts = partService.findwPart(part_id);
    List<BigPart> bigParts=new ArrayList<>();
    for(int i=0;i<parts.size();i++){
        BigPart temp= new BigPart();
        temp.setwPart(parts.get(i));

        Workshop workshop= parts.get(i).getWorkshop();
        temp.setWorkshop(workshop);
        bigParts.add(temp);
    }
    model.addAttribute("bigParts",bigParts);
    model.addAttribute("partName",partName);
    return "search_part";
    }

    @GetMapping("/createAppointment")
    public String createAppointment(){
        return "create_appointment";
    }

    @GetMapping("/getCurrentLocation")
    public  String getCurrentLocation(){
        return "get_current_location";
    }

    @PostMapping("/getCurrentLocation")
    public String getCurrentLocationPost(@RequestParam("lat")String lat,
                                         @RequestParam("long")String lon,
                                         RedirectAttributes ra){

        HttpSession session =request.getSession();
        String email = (String) session.getAttribute("email");
        Workshop workshop = workshopService.get(email);
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        System.out.println("Lat is " + latitude);
        System.out.println("Lon is " + longitude);
        workshop.setLatitude(latitude);
        workshop.setLongitude(longitude);
        System.out.println(workshop);
        workshopService.updateWorkshop(workshop);
        ra.addFlashAttribute("success","Location added Successfully!");
        return "redirect:/workshopProfile";
    }

    @GetMapping("/showCurrentLocationUser")
    public String showCurrentLocationUser(){
        return "get_current_location_user";
    }
    @PostMapping("/getCurrentLocationUser")
    public String getCurrentLocationUserPost(@RequestParam("lat")String lat,
                                         @RequestParam("long")String lon,
                                         RedirectAttributes ra){

        HttpSession session =request.getSession();
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        System.out.println("Lat is " + latitude);
        System.out.println("Lon is " + longitude);
        session.setAttribute("lat",latitude);
        session.setAttribute("lon",longitude);
        ra.addFlashAttribute("success","Location added Successfully!");
        return "redirect:/createAppointment";
    }
    @GetMapping("/showNearbyWorkshops")
    public String showNearbyWorkshops(Model model){
        HttpSession session= request.getSession();
        List<Workshop_Distance> wd = new ArrayList<>();
        List<Workshop> workshops = workshopService.allWorkshops();
        for(int i=0;i<workshops.size();i++){
            Workshop_Distance temp=new Workshop_Distance();
            temp.setWorkshop(workshops.get(i));
            double workshopLat = workshops.get(i).getLatitude();
            double workshopLon = workshops.get(i).getLongitude();
            double userLat = (double) session.getAttribute("lat");
            double userLon = (double) session.getAttribute("lon");
            double latDistance = Math.toRadians(workshopLat - userLat);
            double lonDistance = Math.toRadians(workshopLon - userLon);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(workshopLat))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = 6371 * c;

            temp.setDistance(distance);
            wd.add(temp);
        }
        model.addAttribute("workshops",wd);
        return "create_appointment";
    }
    @PostMapping("/createAppointment")
    public String createAppointmentPost(@RequestParam("workshopId") String id,
                                        RedirectAttributes ra){
        HttpSession session = request.getSession();
        String email= (String) session.getAttribute("email");
        Integer workshopId=Integer.parseInt(id);
        Workshop workshop=workshopService.findById(workshopId);
        User user = userService.findByEmail(email);
        Appointment appointment=new Appointment();
        Date currentDate= new Date();
        appointment.setDate(currentDate);
        appointment.setUser(user);
        appointment.setWorkshop(workshop);
        userService.saveAppointment(appointment);
        ra.addFlashAttribute("success","Appointment Fixed Successfully");
        return "redirect:/userProfile";
    }

}
