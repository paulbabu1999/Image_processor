package controller;

/**
 * Enumeration representing common image file extensions.
 */
public enum Extension {
  JPG("jpg"),
  JPEG("jpeg"),
  PNG("png"),
  GIF("gif"),
  BMP("bmp"),
  PPM("ppm"),
  UNKNOWN("unknown");

  private final String extension;

  Extension(String extension) {
    this.extension = extension;
  }

  /**
   * Retrieves an `model.Extension` enum value based on a given file extension string.
   *
   * @param extension The file extension string to match with enum values.
   * @return The matching `model.Extension` enum value, or `UNKNOWN` if no match is found.
   */
  public static Extension fromExtension(String extension) {
    for (Extension format : values()) {
      if (format.getExtension().equalsIgnoreCase(extension)) {
        return format;
      }
    }
    return UNKNOWN;
  }

  /**
   * Gets the file extension associated with this `model.Extension` enum value.
   *
   * @return The file extension as a string.
   */
  public String getExtension() {
    return extension;
  }
}