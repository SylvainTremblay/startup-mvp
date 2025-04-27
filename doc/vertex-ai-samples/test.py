def is_leap_year(year):
  """
  Determines if a year is a leap year.

  Args:
    year: The year to check.

  Returns:
    True if the year is a leap year, False otherwise.
  """

  # A year is a leap year if it is divisible by 4, unless it is divisible by 100
  # unless it is also divisible by 400.
  print("year:", year)
  if year % 4 == 0:
    if year % 100 == 0:
      return year % 400 == 0
    else:
      return True
  else:
    return False

print(is_leap_year(2000))
print(is_leap_year(2001))
print(is_leap_year(2002))
print(is_leap_year(2003))
