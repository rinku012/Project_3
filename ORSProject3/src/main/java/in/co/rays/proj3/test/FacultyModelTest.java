package in.co.rays.proj3.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.FacultyModelInt;
import in.co.rays.proj3.model.ModelFactory;

public class FacultyModelTest {

	public static FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException, ParseException, RecordNotFoundException {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByLogin();
		 //testFindByPk();
		// testSearch();
		 //testList();

	}

	public static void testList() throws ApplicationException {
		List list = new ArrayList();

		FacultyDTO bean = new FacultyDTO();
		
		list = model.list(1, 5);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (FacultyDTO) it.next();
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLoginId());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getId());
			System.out.println(bean.getCreatedBy());
		}
	}
		
	

	public static void testSearch() throws ApplicationException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		//Date date = sdf.parse("12/01/2018");
		List list = new ArrayList();
		FacultyDTO bean = new FacultyDTO();
		System.out.println("bean");
		bean.setFirstName("RAKESH");
		
		list = model.search(bean, 1, 4);
		System.out.println("serch");
		Iterator it = list.iterator();
		System.out.println("iterator");
		FacultyDTO Bean = null;
		while (it.hasNext()) {
			Bean = (FacultyDTO) it.next();
			System.out.println(Bean.getFirstName());
			System.out.println(Bean.getLastName());
			System.out.println(Bean.getLoginId());
			System.out.println(Bean.getCollegeName());
			System.out.println(Bean.getSubjectName());
			System.out.println(Bean.getId());
			System.out.println(Bean.getMobileNo());
			System.out.println(Bean.getCreatedBy());
			System.out.println(Bean.getModifiedBy());
		}
		
	}

	public static void testFindByPk() throws ApplicationException, RecordNotFoundException {
FacultyDTO bean = new FacultyDTO();
		
		bean = model.findByPk(1);
		System.out.println(bean == null);
		if (bean == null) {
			throw new RecordNotFoundException("no record exist...!!!! plz enter a correct id");
		}
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLoginId());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getId());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		
	}

	public static void testFindByLogin() throws ApplicationException {
		FacultyDTO bean = new FacultyDTO();

		bean = model.findByLogin("RJ@gmail.com");
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLoginId());
		System.out.println(bean.getDoj());

		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getId());

	}

	public static void testUpdate() throws ParseException, ApplicationException, DuplicateRecordException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = sdf.parse("01/12/2018");
			FacultyDTO bean = new FacultyDTO();

			bean.setId(1);
			bean.setFirstName("priyadarshni");
			bean.setLastName("singh");
			bean.setLoginId("psashok@gmail.com");
			bean.setDoj(date);
			bean.setMobileNo("8770145589");
			bean.setSubjectName("digital communication");
			bean.setCollegeName("S");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			System.out.println("Update succes");
		} catch (ApplicationException e) {
			e.printStackTrace();

		}
	}

	public static void testAdd() throws ApplicationException, DuplicateRecordException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse("01/12/2018");
		long l = 0;

		FacultyDTO bean = new FacultyDTO();

		bean.setFirstName("Rajendr");
		bean.setLastName("Yadav");
		bean.setLoginId("RY@gmail.com");
		bean.setDoj(date);
		bean.setMobileNo("9713827472");
		bean.setCollegeId(3);
		bean.setCollegeName("IET-DAVV");
		bean.setSubjectId(1);
		bean.setSubjectName("VLSI");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		l = model.add(bean);
		System.out.println("model add" + l);

	}

}
