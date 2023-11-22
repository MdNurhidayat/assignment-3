package enums;

public enum Faculty {
  SCSE("SCSE"),
  ADM("ADM"),
  EEE("EEE"),
  NBS("NBS"),
  SSS("SSS"),
  NTU("NTU");

  String attribute;
  Faculty(String attributeValue) {
    attribute = attributeValue;
  }
  
  public static Faculty getFacultyForAttribute(String attributeValue) {
    for(Faculty f: Faculty.values()) {
      if(f.attribute == attributeValue) {
        return f;
      }
    }
    throw new IllegalArgumentException("No faculty found for given value of " + attributeValue);
  }
}
