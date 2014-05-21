package org.danisoft.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.danisoft.dao.IAccountDao;
import org.danisoft.model.Account;
import org.danisoft.model.Movement;
import org.danisoft.services.IAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsServiceImpl implements IAccountsService {

	@Autowired
	private IAccountDao accountDao;
	
	/**
	 * @param accountDao the accountDao to set
	 */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountDao.findAll();
	}

	@Override
	public void deleteAccount(Account account) {
		accountDao.delete(account);
	}

	@Override
	public void saveAccount(Account account) {
		accountDao.save(account);
	}

	@Override
	public List<Movement> loadMovementsFromFile(Account account,
			InputStream inputStream) {
		List<Movement> result = new ArrayList<Movement>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			int rows = sheet.getPhysicalNumberOfRows();
			String accountNumber = sheet.getRow(0).getCell(1).getStringCellValue();
			
			if (account.getNumber().equals(accountNumber)) {
				for (int i = 5; i < rows; i++) {
					HSSFRow row = sheet.getRow(i);
					result.add(
							new Movement(
									row.getCell(0).getDateCellValue(), 
									row.getCell(1).getDateCellValue(),
									row.getCell(2).getStringCellValue(),
									row.getCell(3).getNumericCellValue()));
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
