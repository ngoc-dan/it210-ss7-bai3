package ss7.bai3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ss7.bai3.model.Food;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/merchant/food")
public class FoodController {

    private static List<Food> foodList = new ArrayList<>();
    private static final String UPLOAD_PATH = "C:\\Users\\DoKhacQuyen\\Documents\\JAVAWEb\\Session7\\src\\main\\resources\\";
    @GetMapping("/add")
    public String showAddForm(Model model) {
        // Chuẩn bị sẵn một object Food trống để bind vào form
        model.addAttribute("food", new Food());

        // Chuẩn bị danh sách categories cho dropdown
        List<String> categories = Arrays.asList("Khai vị", "Món chính", "Đồ uống", "Tráng miệng");
        model.addAttribute("categories", categories);

        return "bai3"; // Trả về file food-add.html
    }
    @PostMapping("/add")
    public String addFood(@ModelAttribute("food") Food food,
                          @RequestParam("image") MultipartFile file,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        // 1. Kiểm tra ảnh trống
        if (file.isEmpty()) {
            model.addAttribute("error", "Vui lòng đính kèm hình ảnh món ăn!");
            return "food-add";
        }
        // 2. Kiểm tra định dạng file (Chỉ image/*)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            model.addAttribute("error", "Định dạng file không hợp lệ (chỉ chấp nhận ảnh)!");
            return "food-add";
        }
        // 3. Kiểm tra giá tiền (>= 0)
        if (food.getPrice() < 0) {
            model.addAttribute("error", "Giá tiền không được phép âm!");
            return "food-add";
        }
        try {
            File uploadDir = new File(UPLOAD_PATH);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File destination = new File(uploadDir, uniqueFileName);
            file.transferTo(destination);
            food.setImageUrl(destination.getAbsolutePath());
            foodList.add(food);
            redirectAttributes.addFlashAttribute("successMsg", "Thêm món ăn thành công!");
            redirectAttributes.addFlashAttribute("newFood", food);
            return "redirect:/model/Food";
        } catch (IOException e) {
            model.addAttribute("error", "Lỗi lưu file: " + e.getMessage());
            return "bai3";
        }
    }
}