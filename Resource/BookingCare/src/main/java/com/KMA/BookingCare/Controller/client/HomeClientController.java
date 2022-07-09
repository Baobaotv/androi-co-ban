package com.KMA.BookingCare.Controller.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.KMA.BookingCare.Mapper.UserMapper;
import com.KMA.BookingCare.ServiceImpl.UserDetailsImpl;
import io.jsonwebtoken.lang.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KMA.BookingCare.Api.form.searchDoctorForm;
import com.KMA.BookingCare.Api.form.searchHandbookForm;
import com.KMA.BookingCare.Dto.CommentDto;
import com.KMA.BookingCare.Dto.HandbookDto;
import com.KMA.BookingCare.Dto.HospitalDto;
import com.KMA.BookingCare.Dto.InteractiveDto;
import com.KMA.BookingCare.Dto.MedicalExaminationScheduleDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Service.CommentService;
import com.KMA.BookingCare.Service.HandbookService;
import com.KMA.BookingCare.Service.HospitalService;
import com.KMA.BookingCare.Service.InteractiveService;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;
import com.KMA.BookingCare.Service.SpecializedService;
import com.KMA.BookingCare.Service.UserService;
import com.KMA.BookingCare.ServiceImpl.InteractiveServiceImpl;

@Controller
public class HomeClientController {
	
//	@Autowired
//	private SpecializedService specializedDerviceImpl;

	private final Logger log = LoggerFactory.getLogger(HomeClientController.class);
	
	@Autowired
	private UserService UserviceImpl;
	@Autowired
	private HandbookService handbookServiceImpl;
	@Autowired
	private  HospitalService hospitalServiceImpl;
	@Autowired
	private MedicalExaminationScheduleService medicalServiceImpl;
	@Autowired
	private SpecializedService specializedServiceImpl;
	@Autowired
	private InteractiveService interactiveServiceImpl;
	@Autowired
	private CommentService commentServiceimpl;

	//done
	@GetMapping(value = "/home")
	public String homeClientPage(Model model,@RequestParam(required = false) String message,HttpSession session){
		log.info("Reuqest to home page");
		//
		List<SpecializedDto> lstChuyenKhoa = specializedServiceImpl.findAll();
		model.addAttribute("lstChuyenKhoa",lstChuyenKhoa);
		//
		List<HandbookDto> lstCamNang=handbookServiceImpl.findAllByStatus(1);
		model.addAttribute("lstCamNang",lstCamNang);

		List<HospitalDto> lstBenhVien=hospitalServiceImpl.findAllByStatus(1);
		model.addAttribute("lstBenhVien",lstBenhVien);

		List<User> lstBacsi= UserviceImpl.findRandomDoctor();
		model.addAttribute("lstBacsi",lstBacsi);
		//
		List<HandbookDto> lstCamNangRandom=handbookServiceImpl.findRandomHandbook();
		model.addAttribute("lstCamNangRandom",lstCamNangRandom);
		if(message!=null) {
			if(message.equals("bookingsuccess")) {
				String alert="Bạn đã đặt lịch thành công";
				model.addAttribute("alert", alert);
			}
		}
		try {
			Object result = SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (result != null && !result.equals("anonymousUser")) {
				UserDetailsImpl user = (UserDetailsImpl) result;
				MyUser userDetails = UserMapper.convertToMyUser(user);
				session.setAttribute("userDetails", userDetails);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			log.error("User chưa đăng nhập!!!");
		}
		return "client/views/home";
	}

	//done
	@GetMapping(value = "/chuyen-khoa")
	public String specializedPage(Model model,@RequestParam(required = false,name = "page",defaultValue = "1") Integer page){
		Pageable pageable = PageRequest.of(page-1, 4);
		List<SpecializedDto> lstDto = specializedServiceImpl.findAllByStatus(1, pageable);
		model.addAttribute("lstDto",lstDto);
		Integer curentPage=page;	
		model.addAttribute("curentPage", curentPage);
		return "client/views/specialized";
	}
	//done
	@GetMapping(value="/chuyen-khoa/{id}")
	public String  lstDoctorSpecialized(Model model,@PathVariable("id") Long id){
		//get all docter of sprcialzed
		List<User> lstDto=UserviceImpl.findAllDoctorOfSpecialized(id,1);
//		List<User> lstDto=UserviceImpl.findAllBySpecializedIdAndStatus(id, 1);
		model.addAttribute("lstDto",lstDto);
	  System.out.println("test");
	    return "client/views/doctorOfSpecialized";
	}

	//done
	@GetMapping(value="/benh-vien/{id}")
	public String  lstDoctorHospital(Model model,@PathVariable("id") Long id){
		List<User> lstDto=UserviceImpl.findAllDoctorOfHospital(id,1);
//		List<User> lstDto=UserviceImpl.findAllBySpecializedIdAndStatus(id, 1);
		model.addAttribute("lstDto",lstDto);
	  System.out.println("test");
	    return "client/views/doctorOfSpecialized";
	}

	//done
	@GetMapping(value="/bac-si/{id}")
	public String  infoDoctor(Model model,@PathVariable("id") Long id){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(new Date());
		User user=UserviceImpl.findOneDoctorAndWorktime(id);
		model.addAttribute("user", user);
	
	  System.out.println("test");
	    return "client/views/infoDoctor";
	}
	
	@GetMapping(value = "/danh-cho-benh-nhan")
	public String forPatient(Model model){
		return "client/views/forPatient";
	}
	@GetMapping(value = "/vai-tro")
	public String role(Model model){
		return "client/views/role";
	}
	
	@GetMapping(value = "/lien-he")
	public String contect(Model model){
		return "client/views/contect";
	}
	
	@GetMapping(value = "/dieu-khoan")
	public String rules(Model model){
		return "client/views/rules";
	}
	//done
	@GetMapping(value = "/bac-si")
	public String doctor(Model model,@RequestParam(required = false,name = "page",defaultValue = "1") Integer page
			,@ModelAttribute searchDoctorForm form){
		Pageable pageable = PageRequest.of(page-1, 4);
		List<User> lstDto = UserviceImpl.searchDoctorAndPageable(form,pageable);
		List<SpecializedDto> lstChuyenKhoa = specializedServiceImpl.findAll();
		List<HospitalDto> lstBenhvien= hospitalServiceImpl.findAllByStatus(1);
		model.addAttribute("lstBenhvien", lstBenhvien);
		model.addAttribute("lstChuyenKhoa", lstChuyenKhoa);
		model.addAttribute("lstDto", lstDto);
		model.addAttribute("formSearch",form);
		Integer curentPage=page;
		model.addAttribute("curentPage", curentPage);
		
		return "client/views/doctor";
	}
	
//	 @PostMapping(value = "/searchDoctor")
//	   public String searchDoctor(@ModelAttribute searchDoctorForm form,Model model) {
//		   System.out.println("hohi");
//			List<User> lstDto = UserviceImpl.searchDoctor(form);
//			List<SpecializedDto> lstChuyenKhoa = specializedServiceImpl.findAll();
//			List<HospitalDto> lstBenhvien= hospitalServiceImpl.findAllByStatus(1);
//			model.addAttribute("lstBenhvien", lstBenhvien);
//			model.addAttribute("lstChuyenKhoa", lstChuyenKhoa);
//			model.addAttribute("lstDto", lstDto);
//		   return "/client/views/doctor";
//	 }

	//done
	@GetMapping(value = "/benh-vien")
	public String hospital(Model model,@RequestParam(required = false,name = "page",defaultValue = "1") Integer page){
		Pageable pageable = PageRequest.of(page-1, 4);
		List<HospitalDto> lstDto = hospitalServiceImpl.findAllByStatus(1, pageable);
		model.addAttribute("lstDto", lstDto);
		Integer curentPage=page;	
		model.addAttribute("curentPage", curentPage);
		return "client/views/hospital";
	}
	
	
	@GetMapping(value = "/khieu-nai")
	public String complain(Model model){
		return "client/views/complain";
	}

	//done
	@GetMapping(value = "/cam-nang")
	public String handbookPage(Model model
			,@RequestParam(required = false,name = "page",defaultValue = "1") Integer page
			,@ModelAttribute searchHandbookForm form){
		Pageable pageable = PageRequest.of(page-1, 3);
//		List<HandbookDto> lstDto=handbookServiceImpl.findAllByStatusPageable(1,pageable);
		List<HandbookDto> lstDto=handbookServiceImpl.searchHandbookAndPageable(form,null,pageable);
		List<SpecializedDto> lstChuyenKhoa = specializedServiceImpl.findAll();
		model.addAttribute("lstChuyenKhoa",lstChuyenKhoa);
		model.addAttribute("lstDto",lstDto);
		model.addAttribute("formSearch",form);
		Integer curentPage=page;
		model.addAttribute("curentPage", curentPage);
		return "client/views/handbook";
	}
	
	//done
	@GetMapping(value="/cam-nang/{id}")
	public String  contentHandbook(Model model,@PathVariable("id") Long id){
		HandbookDto dto = handbookServiceImpl.findOneById(id);
		List<CommentDto> lstComment= commentServiceimpl.findAllByHandbookId(id);
		model.addAttribute("dto", dto);
		model.addAttribute("lstComment", lstComment);
	    return "client/views/contentHandbook";
	}
	
	@GetMapping(value="/book/{idDoctor}/{idWorktime}/{date}")
	public String  book(Model model,@PathVariable("idDoctor") Long idDoctor, @PathVariable("idWorktime") Long idWorktime,
			@PathVariable("date") String date){
		User userDto = UserviceImpl.findOneById(idDoctor);
		model.addAttribute("userDto", userDto);
		model.addAttribute("date", date);
		model.addAttribute("idWorktime", idWorktime);
		System.out.println("test");
	    return "client/views/book";
	}
	
	@GetMapping(value="/updateProfile")
	public String  updateClient(Model model,HttpSession session){
		MyUser userDetails = (MyUser) session.getAttribute("userDetails");
		User userDto = UserviceImpl.findOneById(userDetails.getId());
		model.addAttribute("userDto", userDto);
	    return "client/views/updateClient";
	}

	@GetMapping(value="/showMedical")
	public String  showMedical(Model model,HttpSession session){
		MyUser userDetails= (MyUser) session.getAttribute("userDetails");
		List<MedicalExaminationScheduleDto> lstDto = medicalServiceImpl.findAllByUserIdAndStatus(userDetails.getId(), 1);
		model.addAttribute("lstDto", lstDto);
	    return "client/views/showMedical";
	}
	
	 
	 
		@GetMapping(value="/myMessage")
		public String  myMessage(Model model,HttpSession session){
			MyUser userDetails= (MyUser) session.getAttribute("userDetails");
			List<InteractiveDto> lstInteractive= interactiveServiceImpl.findAll();
			model.addAttribute("lstInteractive", lstInteractive);
		    return "client/views/message";
		}
}
