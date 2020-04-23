package com.tuyano.springboot;


import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tuyano.springboot.repositories.MyDataRepository;

@Controller
public class HeloController {

	@Autowired        //MyDataRepositoryインスタンスをフィールドに関連づける
	MyDataRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(
			//@ModelAttribute エンティティクラスのインスタンスを自動生成（th:objectで指定する値）
			@ModelAttribute("formModel") MyData mydata,
			ModelAndView mav) {

		mav.setViewName("index");
		mav.addObject("msg", "this is sample");
		Iterable<MyData> list = repository.findAll();
		mav.addObject("datalist", list);

		return mav;
	}

	@RequestMapping(value ="/", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@ModelAttribute("formModel") MyData mydata,
			ModelAndView mav) {
		repository.saveAndFlush(mydata);

		return new ModelAndView("redirect:/");
	}

	//永続ダミーデータ
	@PostConstruct
	public void init() {
		MyData d1 = new MyData();
		d1.setName("tuyano");
		d1.setAge(123);
		d1.setMail("hanada@takeshi");
		d1.setMemo("data!");
		repository.saveAndFlush(d1);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute MyData mydata,
			@PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("titale", "edit mydata");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel", data.get());
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(@ModelAttribute MyData mydata,
			ModelAndView mav) {

		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");

	}


	/*	requestparamを計算して出力

	    @RequestMapping("/{num}")
		public String index(@PathVariable int num) {  //@PathVariable requestparameter(100)取得 localhost:8080/100
			int res = 0;
			for (int i = 1; i <= num; i++) {
				res += i;
			}
			return "total:" + res;
		}
	*/

/*	json形式で出力

    String[] names = {" tuyano", "hanako", "taro", "sakchiko", "ichiro"};

	String[] mails = {"syota@tuuyano.com", "hanako@flower", "taro@yamda", "sachiko@happy", "ichiro@baseball"};

	@RequestMapping("/{id}")
	public DataObject index (@PathVariable int id) {
		return new DataObject(id, names[id], mails[id]);
	}
}

*/

/*  条件式
	@RequestMapping("/{id}")
	public ModelAndView index(@PathVariable int id, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("id",id);               //URLパラメータ
		mav.addObject("check", id % 2 == 0);  //条件
		mav.addObject("trueVal", "Even");     //条件(id%2==0)がtrueであればEvenを返す
		mav.addObject("falseVal", "Odd");     //条件(id%2==0)がfalseであればOddを返す

		return mav;
	}
*/

/*
	@RequestMapping("/{id}")
	public ModelAndView index(@PathVariable int id, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("id",id);               //URLパラメータ
		mav.addObject("check", id >= 0);  //条件
		mav.addObject("trueVal", "positive");     //trueの場合の処理
		mav.addObject("falseVal", "negative");     //falseの場合の処理
		return mav;
	}
*/
/* th:insertで使った
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		return mav;
	}
*/

/*  th:each
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		ArrayList<String[]> data = new ArrayList<String[]>();
		data.add(new String[] {"taro", "taro@yamada", "090-0808-0800"});
		data.add(new String[] {"hanako", "hanako@flower", "090-0753-0800"});
		data.add(new String[] {"sachiko", "sachiko@happy", "090-5698-0800"});
		mav.addObject("data",data);

		return mav;
	}
*/

/* DataObjectに値を代入して、index.htmlに渡す

	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "current data.");
		DataObject obj = new DataObject(123, "hanako", "hanako@flower");
		mav.addObject("object", obj);

		return mav;
	}
*/

}

class DataObject {
	private int id;
	private String name;
	private String value;

	public DataObject(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	/* index.htmlを実行

	@RequestMapping
	public String index() {
		return "index"; //indexという名のtemplatesを探し、読み込む
	}
	*/


	/* addAttributeで変数"msg"の値をhtml側に送る

		@RequestMapping("/{num}")
		public String index(@PathVariable int num, Model model) {

			int res = 0;
			for (int i = 1; i <= num ; i++)
				res += i;


			model.addAttribute("msg", "total: " + res);
			return "index";
		}
	*/

	/* html呼び出し (ガソリンシステムで使ったやつ)

	 	@RequestMapping("/{num}")
 		public ModelAndView index(@PathVariable int num, ModelAndView mav) {
			int res = 0;
			for(int i = 1; i <= num; i++) {
				res += i;
			}
			mav.addObject("msg", "total: " + res); //addObject setAttributeみたいなもの
			mav.setViewName("index");              //html名
			return mav;
		}
	*/


	/*  フォームコントロール

	    //ブラウザ呼び出し時
		@RequestMapping(value = "/", method=RequestMethod.GET)
		public ModelAndView index(ModelAndView mav) {
			mav.setViewName("index");
			mav.addObject("msg", "名前を書いてね♡");
			return mav;
		}

		//request送信時
		@RequestMapping(value="/", method=RequestMethod.POST)
		public ModelAndView send(@RequestParam("text1")String str, ModelAndView mav) {
			mav.addObject("msg", "こんにちは、" +  str + "さん♡");
			mav.setViewName("index");
			return mav;
		}
	*/

	/* フォームコントロール(p.155)

		@RequestMapping(value = "/", method=RequestMethod.GET)
		public ModelAndView index(ModelAndView mav) {
			mav.setViewName("index");
			mav.addObject("msg", "フォームを送信してね♡");
			return mav;
		}

		@RequestMapping(value="/", method=RequestMethod.POST)
		public ModelAndView send(

			@RequestParam(value="check1", required=false)boolean check1,   //value受け取るparam名  requiredは値が渡されないこともある
			@RequestParam(value="radio1", required=false)String radio1,     //@RequestParam 値がないとエラーになるため、required=falseを指定
			@RequestParam(value="select1", required=false)String select1,
			@RequestParam(value="select2", required=false)String[] select2, //
			ModelAndView mav) {

			String res = "";

			try {
				res = "check:" + check1 +
					  " radio:" + radio1 +
					  " select:" + select1 +
					  " \nselect2:";
			} catch (NullPointerException e) {

			}

			try {
				res += select2[0];
				for (int i = 1; i< select2.length; i++) {
					res += ", " +select2[i];
				}
			} catch (NullPointerException e) {
				res += "null";
			}

			mav.addObject("msg", res);
			mav.setViewName("index");
			return mav;
		}
	*/
/*
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		return mav;
	}

	//リダイレクト  クライアント側に送られた後で別のページに移動させるもの アドレスは遷移先のアドレスに変更される
	@RequestMapping("/other")
	public String other() {
		return "redirect:/";
	}

	 //フォワード   サーバー内部で別ページを読み込み表示する。アクセスするアドレスはそのままでページ内容だけ変わる感じ
	@RequestMapping("/home")
	public String home() {
		return "forward:/";
	}*/

}