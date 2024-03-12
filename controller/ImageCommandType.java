package controller;

/**
 * Enumeration representing command types.
 */
public enum ImageCommandType {
  LOAD("Load"),
  SAVE("Save"),
  BRIGHTEN("Brighten"),
  VERTICAL_FLIP("Vertical-Flip"),
  HORIZONTAL_FLIP("Horizontal-Flip"),
  VALUE_COMPONENT("Value-Component"),
  RGB_SPLIT("RGB-Split"),
  RGB_COMBINE("RGB-Combine"),
  SEPIA("sepia"),
  BLUR("blur"),
  SHARPEN("sharpen"),
  INTENSITY_COMPONENT("intensity-component"),
  LUMA_COMPONENT("luma-component"),
  RED_COMPONENT("red-component"),
  GREEN_COMPONENT("green-component"),
  BLUE_COMPONENT("blue-component"),
  HISTOGRAM("histogram"),
  COLOR_CORRECT("color-correct"),
  LEVEL_ADJUST("levels-adjust"),
  COMPRESS("compress"),
  CHOP("chop"),
  UNDERLAY("underlay"),

  UNKNOWN("Unknown");

  private final String description;

  ImageCommandType(String description) {
    this.description = description;
  }

  /**
   * Retrieves an `controller.ImageCommandType` enum value based on a provided string.
   *
   * @param text  The string representation of the command type.
   * @return      The matching `controller.ImageCommandType` enum value or `UNKNOWN` if no match is
   *              found.
   */
  public static ImageCommandType fromString(String text) {
    if (text != null) {
      //text = text.replace("-", " "); // Replace hyphens with spaces
      for (ImageCommandType commandType : ImageCommandType.values()) {
        if (text.equalsIgnoreCase(commandType.getDescription().replace(" ", "-"))) {
          return commandType;
        }
      }
    }
    return UNKNOWN; // Default value
  }

  /**
   * Gets the description associated with this `controller.ImageCommandType` enum value.
   *
   * @return The description as a string.
   */
  public String getDescription() {
    return description;
  }
}