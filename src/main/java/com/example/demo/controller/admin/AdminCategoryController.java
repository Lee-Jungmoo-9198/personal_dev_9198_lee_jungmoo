package com.example.demo.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Controller
public class AdminCategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/admin/categories")
	public String index(Model model) {
		List<Category> categoryList = new ArrayList<>();
		categoryList = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("categoryList", categoryList);

		return "admin/adminCategory";
	}

	@GetMapping("/admin/categories/add")
	public String create() {

		return "admin/adminAddCategory";
	}

	@PostMapping("/admin/categories/add")
	public String store(
			@RequestParam(name = "name", defaultValue = "") String name) {
		Category category = new Category(name);

		categoryRepository.save(category);
		// categories.htmlを開く
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/category/{id}/edit")
	public String update(
			@PathVariable(name = "id") Integer id,
			Model model) {

		// 更新対象のデータを取得
		Optional<Category> dbData = categoryRepository.findById(id);
		// 取得出来なかった場合、商品一覧に戻る
		if (dbData.isEmpty()) {
			return "redirect:/admin/categories";
		}

		Category category = dbData.get();
		// 取得したデータをhtmlであつかえるようにする
		model.addAttribute("category", category);

		return "admin/adminEditCategory";
	}

	// 更新の処理
	@PostMapping("/admin/category/{id}/edit")
	public String update(
			@PathVariable(name = "id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name) {
		Optional<Category> dbData = categoryRepository.findById(id);

		if (!dbData.isEmpty()) {

			Category category = dbData.get();

			category.setName(name);

			categoryRepository.save(category);

		}

		// 商品一覧画面を開く(リダイレクト)
		return "redirect:/admin/categories";
	}

	// 削除処理
	@PostMapping("/admin/category/{id}/delete")
	public String delete(
			@PathVariable(name = "id") Integer id) {
		// データの存在チェック
		Optional<Category> dbData = categoryRepository.findById(id);
		// データが取得できたら削除を実施
		if (!dbData.isEmpty()) {
			// リポジトリ.deleteById(id);s
			categoryRepository.deleteById(id);
		}
		return "redirect:/admin/categories";
	}
}
