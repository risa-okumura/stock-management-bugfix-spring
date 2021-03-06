package jp.co.rakus.stockmanagement.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.stockmanagement.domain.Book;
import jp.co.rakus.stockmanagement.service.BookService;

/**
 * 書籍関連処理を行うコントローラー.
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/book")
@Transactional
public class BookController {
	
	@Autowired
	private BookService bookService;

	/**
	 * フォームを初期化します.
	 * @return フォーム
	 */
	@ModelAttribute
	public BookForm setUpForm() {
		return new BookForm();
	}
	
	/**
	 * 書籍リスト情報を取得し書籍リスト画面を表示します.
	 * @param model モデル
	 * @return 書籍リスト表示画面
	 */
	@RequestMapping(value = "list")
	public String list(Model model) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);
		
		
		return "book/list";
	}
	
	/**
	 * 書籍詳細情報を取得し書籍詳細画面を表示します.
	 * @param id 書籍ID
	 * @param model　モデル
	 * @return　書籍詳細画面
	 */
	@RequestMapping(value = "show/{bookId}")
	public String show(@PathVariable("bookId") Integer id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		return "book/show";
	}
	
	/**
	 * 書籍更新を行います.
	 * @param form フォーム
	 * @param result リザルト情報
	 * @param model　モデル
	 * @return　書籍リスト画面
	 */
	@RequestMapping(value = "update")
	public String update(@Validated BookForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return show(form.getId(), model);
		}
		Book book = bookService.findOne(form.getId());
		book.setStock(form.getStock());
		bookService.update(book);
		return list(model);
	}
	
	/**
	 * 書籍情報を登録する.
	 * 
	 * @param form 書籍情報のフォーム
	 * @param result　
	 * @param model
	 * @return　書籍一覧画面
	 */
	@RequestMapping(value = "insertBook")
	public String insertBook(@Validated BookForm form, BindingResult result, Model model) {
		ImageDirectory imageDirectory = new ImageDirectory();
		File file = new File(imageDirectory.filePath("C:\\env\\app\\sts\\stock-management-bugfix-spring\\src\\main\\webapp\\img\\") + form.getImage().getOriginalFilename());
		System.out.println(file);
		try{
			form.getImage().transferTo(file);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		Book book = new Book();
		BeanUtils.copyProperties(form, book);

		List<Book> bookList = bookService.findAll();
		int lastId = bookList.size();
		book.setId((lastId+1));
		
		book.setPrice(form.getIntPrice());
		book.setSaledate(form.getDateSaledate());
		book.setImage(form.getImage().getOriginalFilename());
		System.out.println(book.toString());

		bookService.insertBook(book);
		
		return "redirect:/book/list";
	}
	
}
