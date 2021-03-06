package com.jetbrains.python.edu;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.ide.RecentProjectsManager;
import com.intellij.ide.ui.UISettings;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.platform.templates.github.ZipUtil;
import com.intellij.util.PathUtil;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"UtilityClassWithoutPrivateConstructor", "UtilityClassWithPublicConstructor"})
public class StudyInitialConfigurator {
  private static final Logger LOG = Logger.getInstance(StudyInitialConfigurator.class.getName()
  );
  @NonNls private static final String CONFIGURED_V1 = "StudyPyCharm.InitialConfiguration";
  @NonNls private static final String CONFIGURED_V11 = "StudyPyCharm.InitialConfiguration1.1";


  /**
   * @noinspection UnusedParameters
   */
  public StudyInitialConfigurator(MessageBus bus,
                                  UISettings uiSettings,
                                  CodeInsightSettings codeInsightSettings,
                                  final PropertiesComponent propertiesComponent,
                                  FileTypeManager fileTypeManager,
                                  final ProjectManagerEx projectManager,
                                  RecentProjectsManager recentProjectsManager) {
    final File file = new File(getCoursesRoot(), "introduction_course.zip");
    if (!propertiesComponent.getBoolean(CONFIGURED_V1, false)) {
      final File newCourses = new File(PathManager.getConfigPath(), "courses");
      try {
        FileUtil.createDirectory(newCourses);
        copyCourse(file, newCourses);
        propertiesComponent.setValue(CONFIGURED_V1, "true");

      }
      catch (IOException e) {
        LOG.warn("Couldn't copy bundled courses " + e);
      }
    }
    if (!propertiesComponent.getBoolean(CONFIGURED_V11, false)) {
      final File newCourses = new File(PathManager.getConfigPath(), "courses");
      if (newCourses.exists()) {
        try {
          copyCourse(file, newCourses);
          propertiesComponent.setValue(CONFIGURED_V11, "true");
        }
        catch (IOException e) {
          LOG.warn("Couldn't copy bundled courses " + e);
        }
      }
    }
  }

  private static void copyCourse(File bundledCourse, File userCourseDir) throws IOException {
    String fileName = bundledCourse.getName();
    String unzippedName = fileName.substring(0, fileName.indexOf("."));
    File courseDir = new File(userCourseDir, unzippedName);
    ZipUtil.unzip(null, courseDir, bundledCourse, null, null, true);
  }

  public static File getCoursesRoot() {
    @NonNls String jarPath = PathUtil.getJarPathForClass(StudyInitialConfigurator.class);
    if (jarPath.endsWith(".jar")) {
      final File jarFile = new File(jarPath);


      File pluginBaseDir = jarFile.getParentFile();
      return new File(pluginBaseDir, "courses");
    }

    return new File(jarPath , "courses");
  }

}
