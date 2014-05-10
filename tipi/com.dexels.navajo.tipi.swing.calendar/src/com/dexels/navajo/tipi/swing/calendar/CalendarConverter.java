package com.dexels.navajo.tipi.swing.calendar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.NumberList;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.TextList;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.WeekDayList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import com.miginfocom.calendar.activity.Activity;
import com.miginfocom.calendar.activity.ActivityDepository;
import com.miginfocom.calendar.activity.recurrence.ByXXXRuleData;
import com.miginfocom.calendar.activity.recurrence.CompositeRecurrence;
import com.miginfocom.calendar.activity.recurrence.Recurrence;
import com.miginfocom.calendar.activity.recurrence.RecurrenceRule;
import com.miginfocom.util.dates.ImmutableDateRange;

import edu.emory.mathcs.backport.java.util.Arrays;

public class CalendarConverter {
	private final static Logger logger = LoggerFactory
			.getLogger(CalendarConverter.class);
	
	private final static UidGenerator uidGenerator;
	static
	{
		UidGenerator obj = null;
		try{
			obj = new UidGenerator("Sportlink");
			
		}
		catch(Exception se)
		{
			logger.warn("No uidgenerator, let's cross fingers it isn't needed." , se);
		}
		finally
		{
			uidGenerator = obj;
		}
	}

	public OutputStream exportActivities()
	{
		net.fortuna.ical4j.model.Calendar outputCal = new net.fortuna.ical4j.model.Calendar();
		outputCal.getProperties().add(new ProdId("Sportlink"));
		outputCal.getProperties().add(Version.VERSION_2_0);
		outputCal.getProperties().add(CalScale.GREGORIAN);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<Activity> brokedActivities = ActivityDepository.getInstance().getBrokedActivities();
		for (Activity a : brokedActivities)
		{
			PropertyList properties = new PropertyList();
			addToPropertiesIfNotNull(properties, getUId(a));
			addToPropertiesIfNotNull(properties, getStartDate(a));
			addToPropertiesIfNotNull(properties, getEndDate(a));
			addToPropertiesIfNotNull(properties, getSummary(a));
			addToPropertiesIfNotNull(properties, getStampDate(a));
			addToPropertiesIfNotNull(properties, getCategories(a));
			addToPropertiesIfNotNull(properties, getDescription(a));
			addToPropertiesIfNotNull(properties, getLocation(a));
			
			addRecurrence(properties, a);

			VEvent event = new VEvent(properties);
			outputCal.getComponents().add(event);
		}
		
		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(outputCal, baos);
		} catch (IOException e) {
			logger.warn("Something going wrong outputting the generated calendar.", e);
		} catch (ValidationException e) {
			logger.warn("Something going wrong outputting the generated calendar.", e);
		}
		return baos;
	}
	
	private void addToPropertiesIfNotNull(PropertyList properties, Property p)
	{
		if (p != null)
		{
			properties.add(p);
		}
	}
	
	private void addRecurrence(PropertyList properties, Activity a)
	{
		DateList rDate = new DateList();
		DateList exDate = new DateList();
		Collection<Recur> rRule = new ArrayList<Recur>();
		Collection<Recur> exRule = new ArrayList<Recur>();
		
		if (a.getRecurrence() != null)
		{
			// recursively parse the recurrences
			parseRecurrence(a.getRecurrence(), rDate, exDate, rRule, exRule, Boolean.TRUE);
			if (!rDate.isEmpty())
			{
				properties.add(new RDate(rDate));
			}
			if (!exDate.isEmpty())
			{
				properties.add(new ExDate(exDate));
			}
			for (Recur r : rRule)
			{
				properties.add(new RRule(r));
			}
			for (Recur r : exRule)
			{
				properties.add(new RRule(r));
			}
		}
	}
	
	/**
	 * 
	 * @param r         The recurrence to parse.
	 * @param rDate     Output
	 * @param exDate    Output
	 * @param rRule     Output
	 * @param exRule    Output
	 * @param including Only used if the Recurrence r is of the type RecurrenceRule
	 */
	private void parseRecurrence(Recurrence r, DateList rDate, DateList exDate, Collection<Recur> rRule, Collection<Recur> exRule, Boolean including)
	{
		if (r instanceof CompositeRecurrence)
		{
			CompositeRecurrence compRecur = (CompositeRecurrence) r;
			// need to recurse, but only after we have parsed the dates.
			List<ImmutableDateRange> includedDates = compRecur.getIncludingRanges();
			for (ImmutableDateRange includedDate : includedDates)
			{
				rDate.add(new Date(includedDate.getStartTime()));
			}
			List<ImmutableDateRange> excludedDates = compRecur.getExcludingRanges();
			for (ImmutableDateRange excludedDate : excludedDates)
			{
				exDate.add(new Date(excludedDate.getStartTime()));
			}
			for (Recurrence includedRecur : compRecur.getIncludingRecurrencies())
			{
				parseRecurrence(includedRecur, rDate, exDate, rRule, exRule, true);
			}
			for (Object excludedRecur : compRecur.getExcludingRecurrencies())
			{
				parseRecurrence((Recurrence) excludedRecur, rDate, exDate, rRule, exRule, false);
			}
		}
		else if (r instanceof RecurrenceRule)
		{ // non-recursive part
			if (including == null)
			{
				logger.warn("Cannot determine including or excluding!");
			}
			RecurrenceRule recurRule = (RecurrenceRule) r;
			Recur recur;
			if (recurRule.getRepetitionCount() == null)
			{
				Date untilDate = null;
				if (recurRule.getUntilDate() != null)
				{
					untilDate = new Date(recurRule.getUntilDate().getTime());
				}
				recur = new Recur(translateFrequency(recurRule.getFrequency()), untilDate);
			}
			else
			{
				recur = new Recur(translateFrequency(recurRule.getFrequency()), recurRule.getRepetitionCount());
			}
			recur.setInterval(recurRule.getInterval());
			if (recurRule.getWeekStart() != null)
			{
				recur.setWeekStartDay(translateWeekStartDay(recurRule.getWeekStart()));
			}
			for (ByXXXRuleData data : recurRule.getByXXXRules())
			{
				if (data.getCalendarField() != Calendar.DAY_OF_WEEK)
				{
					NumberList nl = getCorrectNumberList(data.getCalendarField(), recur);
					for (int i : data.getValues())
					{
						nl.add(i);
					}
				}
				else
				{
					WeekDayList nl = recur.getDayList();
					for (int i=0;i<data.getValues().length;i++)
					{
						if ((recurRule.getFrequency() == Calendar.MONTH || recurRule.getFrequency() == Calendar.YEAR) && data.getRelativeWeekDayInYearOrMonth()[i] != 0)
						{
							nl.add(new WeekDay(WeekDay.getDay(data.getValues()[i]), data.getRelativeWeekDayInYearOrMonth()[i]));
						}
						else
						{
							nl.add(WeekDay.getDay(data.getValues()[i]));
						}
					}
				}
			}
			if (recurRule.getBySetPos() != null)
			{
				for (int i : recurRule.getBySetPos())
				{
					// MIG is 0 based, iCal is 1 based
					recur.getSetPosList().add(i + 1);
				}
			}
			if (including)
			{
				rRule.add(recur);
			}
			else
			{
				exRule.add(recur);
			}
		}
	}
	
	private NumberList getCorrectNumberList(int calendarField, Recur recur)
	{
		switch (calendarField) {
			case Calendar.DAY_OF_MONTH : return recur.getMonthDayList();
			case Calendar.MONTH : return recur.getMonthList();
			case Calendar.WEEK_OF_YEAR : return recur.getWeekNoList();
			case Calendar.DAY_OF_YEAR : return recur.getYearDayList();
			case Calendar.HOUR_OF_DAY : return recur.getHourList();
			case Calendar.MINUTE : return recur.getMinuteList();
			case Calendar.SECOND : return recur.getSecondList();
			default : logger.warn("Unidentified calendarField passed to getCorrectNumberList: " + calendarField);
		}
		return null;
	}

	// Translates Calendar constants (int) as used by MIG to String as used in the iCal specs
	private String translateFrequency(int i)
	{
		switch (i) {
			case Calendar.YEAR : return Recur.YEARLY;
			case Calendar.MONTH : return Recur.MONTHLY;
			case Calendar.WEEK_OF_YEAR : return Recur.WEEKLY;
			case Calendar.DAY_OF_YEAR : return Recur.DAILY;
			case Calendar.HOUR_OF_DAY : return Recur.HOURLY;
			case Calendar.MINUTE : return Recur.MINUTELY;
			case Calendar.SECOND : return Recur.SECONDLY;
		}
		return null;
	}
	
	// Translates Calendar constants (int) as used by MIG to String as used in the iCal specs
	private String translateWeekStartDay(int i)
	{
		switch (i) {
			case Calendar.MONDAY : return "MO";
			case Calendar.TUESDAY: return "TU";
			case Calendar.WEDNESDAY : return "WE";
			case Calendar.THURSDAY : return "TH";
			case Calendar.FRIDAY : return "FR";
			case Calendar.SATURDAY : return "SA";
			case Calendar.SUNDAY : return "SU";
		}
		return null;
	}
	
	private Categories getCategories(Activity a)
	{
		TextList tl = new TextList();
		for (Object category : a.getCategoryIDs())
		{
			tl.add(category.toString());
		}
		return new Categories(tl);
	}
	
	private Description getDescription(Activity a)
	{
		return new Description(a.getDescription());
	}
	
	private DtStamp getStampDate(Activity a)
	{
		Long createdMillis = a.getCreatedByStorageDate();
		if (createdMillis == null)
		{
			createdMillis = 0L;
		}
		return new DtStamp(new DateTime(createdMillis));
	}
	
	private DtStart getStartDate(Activity a)
	{
		return new DtStart(new DateTime(a.getBaseDateRange().getStartTime()));
	}

	private DtEnd getEndDate(Activity a)
	{
		return new DtEnd(new DateTime(a.getBaseDateRange().getEndTime()));
	}
	
	private Location getLocation(Activity a)
	{
		return new Location(a.getLocation());
	}
	
	private Summary getSummary(Activity a)
	{
		return new Summary(a.getSummary());
	}

	private Uid getUId(Activity a)
	{
		Object id = a.getID();
		Uid uid;
		if (id == null)
		{
			uid = uidGenerator.generateUid(); 
		}
		else
		{
			uid = new Uid(id.toString());
		}
		return uid;
	}

}
