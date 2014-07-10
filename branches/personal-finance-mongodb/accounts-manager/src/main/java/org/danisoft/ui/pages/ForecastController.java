package org.danisoft.ui.pages;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;

@Controller
@Scope("prototype")
public class ForecastController implements Initializable {
	@FXML
	private ComboBox<String> monthCombo;
	@FXML
	private ComboBox<String> yearCombo;
	@FXML
	private BarChart<String, Number> monthlyChart;
	
	//Chart Series
	private Series<String, Number> serie;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		monthCombo.setItems(FXCollections.observableArrayList(DateFormatSymbols.getInstance().getMonths()));
		monthCombo.setOnAction(e -> monthChanged());
		
		yearCombo.setItems(
				FXCollections.observableArrayList(
						IntStream
						.range(2000, 2050)
						.boxed()
						.map(i -> i.toString())
						.collect(Collectors.toList())));
		yearCombo.setOnAction(e -> monthChanged());
		
		serie = new Series<>();
		serie.setName("Serie");
	
		monthlyChart.getData().addAll(serie);
		monthlyChart.setLegendVisible(false);
		
		cache = new HashMap<>();
	}

	Map<String, Map<String, Map<String, Number>>> cache;
	
	private void monthChanged() {
		String year = yearCombo.getValue();
		String month = monthCombo.getValue();
		
		if (year == null || month == null) return;
		
		Random random = new Random(System.currentTimeMillis());
		
		if (!cache.containsKey(year)) {
			cache.put(year, new HashMap<String, Map<String,Number>>());
		}
		if (!cache.get(year).containsKey(month)) {
			cache.get(year).put(month, new HashMap<>());
		}
		if (!cache.get(year).get(month).containsKey("expense")) {
			cache.get(year).get(month).put("expense", random.nextDouble());
		}
		if (!cache.get(year).get(month).containsKey("income")) {
			cache.get(year).get(month).put("income", random.nextDouble());
		}
		
		Number expense = cache.get(year).get(month).get("expense");
		Number income = cache.get(year).get(month).get("income");
		Number balance = income.doubleValue() - expense.doubleValue();
		
		this.serie.getData().clear();
		
		this.serie.getData().add(new Data<String, Number>("expense", expense));
		this.serie.getData().add(new Data<String, Number>("income", income));
		this.serie.getData().add(new Data<String, Number>("balance", balance));
	}

}
