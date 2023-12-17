package com.morozantonius.alarmc;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModViewModel extends ViewModel {
        private String alarmSetMessage;
        private List<Calendar> alarms = new ArrayList<>();

        public String getAlarmSetMessage() {
                return alarmSetMessage;
        }

        public void setAlarmSetMessage(String message) {
                alarmSetMessage = message;
        }

        public List<Calendar> getAlarms() {
                return alarms;
        }

        public void addAlarm(Calendar calendar) {
                alarms.add(calendar);
        }

        public void removeAlarm(Calendar calendar) {
                alarms.remove(calendar);
        }
}
