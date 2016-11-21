package com.imgretrievaldemo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class MainController {

	IndexRunner runner = null;
	
	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("tabColorAvailable", Tools.getIndexes('C').toString());
		model.put("tabGrayAvailable", Tools.getIndexes('G').toString());
		model.put("resultSearch", "null");
		return "index";
	}
	
	@PostMapping("/")
	public String search(Map<String, Object> model, @RequestParam("file") MultipartFile file,@RequestParam("type") String type,@RequestParam("bin") Integer bin) {
		model.put("tabColorAvailable", Tools.getIndexes('C').toString());
		model.put("tabGrayAvailable", Tools.getIndexes('G').toString());
		model.put("resultSearch", Tools.search(file, type, bin));
		return "index";
	}
	
	@GetMapping("/create")
	public String create(Map<String, Object> model) {
		model.put("stateRunner", runner!=null?runner.getState():"null");
		model.put("tabColorAvailable", Tools.getIndexes('C'));
		model.put("tabGrayAvailable", Tools.getIndexes('G'));
		return "createindex";
	}
	
	@GetMapping("/delete")
	public RedirectView deleteIndex(Map<String, Object> model, @RequestParam("type") String type,@RequestParam("bin") Integer bin) {
		Tools.deleteIndex(type, bin);
		return new RedirectView("/create");
	}
	
	@PostMapping("/createIndex")
	public RedirectView createIndex(Map<String, Object> model, @RequestParam("type") String type,@RequestParam("bin") Integer bin){
		runner = new IndexRunner("index", type, bin);
		runner.start();
		return new RedirectView("/create");
	}
	
}