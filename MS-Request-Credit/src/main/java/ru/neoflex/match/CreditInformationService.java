package ru.neoflex.match;

import org.springframework.stereotype.Component;
import ru.neoflex.openapi.model.PaymentScheduleElement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Component
public class CreditInformationService {
    private  double monthRate;
    public double getMonthRate() {
        return monthRate;
    }
    public void setMonthRate(BigDecimal rate) {
        this.monthRate = (rate.doubleValue() / 12) / 100;;
    }
    public double getKoefficient(BigDecimal rate, int term) {
        this.setMonthRate(rate);
        double koffeciantAutet = (monthRate * Math.pow((1 + monthRate), term)) / ((Math.pow((1 + monthRate), term)) - 1);
        return koffeciantAutet;
    }
    public BigDecimal GetPSk(BigDecimal rate,BigDecimal amount, int term)
    {
        return getMonthPaymant(rate,amount,term).multiply(BigDecimal.valueOf(term));
    }

    public BigDecimal getMonthPaymant(BigDecimal rate,BigDecimal amount, int term){
        return amount.multiply(BigDecimal.valueOf(this.getKoefficient(rate,term)));
    }
    public List<PaymentScheduleElement> getListPaymentScheduleElement(int term, BigDecimal amount, BigDecimal rate) {
        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<PaymentScheduleElement>();

        double procent = 0;
        Calendar dateForPayment = Calendar.getInstance();
        TimeZone tz = dateForPayment.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        for (int i = 0; i < term; i++) {
            if (i == 0) {
                PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
                paymentScheduleElement.setTotalPayment(getMonthPaymant(rate,amount,term));
                paymentScheduleElement.setNumber(i + 1);
                paymentScheduleElement.setDate(LocalDate.now());
                paymentScheduleElement.setInterestPayment(amount.multiply(BigDecimal.valueOf(monthRate)));
                procent = paymentScheduleElement.getInterestPayment().doubleValue();
                paymentScheduleElement.setDebtPayment(paymentScheduleElement.getTotalPayment().add(paymentScheduleElement.getInterestPayment().negate()));
                paymentScheduleElement.setRemainingDebt(amount.add(paymentScheduleElement.getDebtPayment().negate()));
                paymentScheduleElementList.add(paymentScheduleElement);

            } else {
                int n = i - 1;
                PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
                paymentScheduleElement.setNumber(i + 1);
                dateForPayment.add(Calendar.MONTH, 1);
                paymentScheduleElement.setDate(LocalDate.ofInstant(dateForPayment.toInstant(), zid));
                paymentScheduleElement.setInterestPayment(paymentScheduleElementList.get(n).getRemainingDebt().multiply(BigDecimal.valueOf(monthRate)));
                procent = procent + paymentScheduleElement.getInterestPayment().doubleValue();
                paymentScheduleElement.setDebtPayment(paymentScheduleElementList.get(n).getTotalPayment().add(paymentScheduleElement.getInterestPayment().negate()));
                paymentScheduleElement.setRemainingDebt(paymentScheduleElementList.get(n).getRemainingDebt().add(paymentScheduleElement.getDebtPayment().negate()));
                paymentScheduleElement.setTotalPayment(paymentScheduleElementList.get(n).getTotalPayment());
                if ((i == term - 1)) {
                    paymentScheduleElement.setRemainingDebt(BigDecimal.ZERO);
                } else {
                    paymentScheduleElement.setRemainingDebt(paymentScheduleElementList.get(n).getRemainingDebt().add(paymentScheduleElement.getDebtPayment().negate()));
                }
                paymentScheduleElementList.add(paymentScheduleElement);
            }


        }
        return paymentScheduleElementList;
    }

}
