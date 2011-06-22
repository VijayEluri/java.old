#include <time.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char* argv[]) {
    // time_t, representing the number of seconds elapsed since 00:00 hours, Jan 1, 1970 UTC.
    time_t seconds;

    seconds = time (NULL);
    printf("%ld hours since January 1, 1970\n", seconds/3600);

    // tm , Structure containing a calendar date and time broken down into its components.
    //  Member      Meaning                     Range
    //  tm_sec      seconds after the minute    0-61*
    //  tm_min      minutes after the hour      0-59
    //  tm_hour     hours since midnight        0-23
    //  tm_mday     day of the month            1-31
    //  tm_mon      months since January        0-11
    //  tm_year     years since 1900
    //  tm_wday     days since Sunday           0-6, (Sunday =0)
    //  tm_yday     days since January 1        0-365
    //  tm_isdst    Daylight Saving Time flag
    //
    //  * tm_sec is generally 0-59. Extra range to accommodate for leap seconds in certain systems.
    struct tm * timeinfo = localtime(&seconds);
    printf("Current local time and date: %s\n", asctime(timeinfo));

    char time_str[20];
    sprintf(time_str, "%04d-%02d-%02d %02d:%02d:%02d",
            timeinfo->tm_year + 1900,  // tm_year, years since 1900
            timeinfo->tm_mon + 1, // tm_mon, months since January, range 0-11
            timeinfo->tm_mday, // tm_mday, day of the month, range 1-31
            timeinfo->tm_hour, // tm_hour, hours since midnight, range 0-23
            timeinfo->tm_min, // tm_min, minutes after the hour, range 0-59
            timeinfo->tm_sec); // tm_sec, seconds after the minute, range 0-61

    printf("Current time: %s\n", time_str);
    printf("    It the %d day of the week\n", timeinfo->tm_wday + 1);
    printf("    It the %d day of the year\n", timeinfo->tm_yday + 1);

    // use of function strftime
    memset(time_str, 0, sizeof(time_str));
    strftime(time_str, sizeof(time_str), "%Y-%m-%d %H:%M:%S", timeinfo);
    printf("Current time: %s\n", time_str);

    return 0;

}


