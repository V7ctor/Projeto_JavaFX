package GUI.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Utils {

	public static Stage stageAtual(ActionEvent evento) {
		return (Stage) ((Node) evento.getSource()).getScene().getWindow();
	}

	public static Integer converToInt(String arg) {
		try {
			return Integer.parseInt(arg);

		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <T> void formatarTabelaData(TableColumn<T, Date> colunaTabela, String formato) {
		colunaTabela.setCellFactory(coluna -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(formato);

				@Override
				protected void updateItem(Date item, boolean vazio) {
					super.updateItem(item, vazio);
					if (vazio) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}

	public static <T> void formatarTabelaDouble(TableColumn<T, Double> colunaTabela, int casasDecimais) {
		colunaTabela.setCellFactory(coluna -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean vazio) {
					super.updateItem(item, vazio);
					if (vazio) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + casasDecimais + "f", item));
					}
				}
			};
			return cell;
		});
	}

	public static void formatarDatePicker(DatePicker datePicker, String formato) {
		datePicker.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern(formato);
			{
				datePicker.setPromptText(formato.toLowerCase());
			}

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return formatoData.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, formatoData);
				} else {
					return null;
				}
			}
		});
	}

}
