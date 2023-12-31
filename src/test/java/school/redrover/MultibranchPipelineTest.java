package school.redrover;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.model.MainPage;
import school.redrover.model.jobsConfig.MultibranchPipelineConfigPage;
import school.redrover.model.jobs.MultibranchPipelinePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String NAME = "MultibranchPipeline";
    private static final String RENAMED = "MultibranchPipelineRenamed";
    private static final String DESCRIPTION = "Description";

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Find created Multibranch Pipeline project on Main page")
    @Test
    public void testFindCreatedMultibranchPipelineOnMainPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean jobIsPresent = new MainPage(getDriver())
                .jobIsDisplayed(NAME);

        Assert.assertTrue(jobIsPresent, "The Multibranch Pipeline's name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be renamed from drop down menu on the Main page")
    @Test
    public void testRenameFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualProjectName = new MainPage(getDriver())
                .dropDownMenuClickRename(NAME, new MultibranchPipelinePage(getDriver()))
                .enterNewName(RENAMED)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(actualProjectName, RENAMED);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be renamed from side menu on the Project page")
    @Test
    public void testRenameFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualDisplayedName = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickRename()
                .enterNewName(RENAMED)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(actualDisplayedName, RENAMED);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Multibranch Pipeline project from drop-down menu with existing name")
    @Test
    public void testRenameToTheCurrentNameAndGetError() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String errorMessage = new MainPage(getDriver())
                .dropDownMenuClickRename(NAME, new MultibranchPipelinePage(getDriver()))
                .enterNewName(NAME)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "The new name is the same as the current name.");
    }

    @DataProvider(name = "wrong-character")
    public Object[][] provideWrongCharacters() {
        return new Object[][]{{"!", "!"}, {"@", "@"}, {"#", "#"}, {"$", "$"}, {"%", "%"}, {"^", "^"}, {"&", "&amp;"}, {"*", "*"},
                {"?", "?"}, {"|", "|"}, {">", "&gt;"}, {"<", "&lt;"}, {"[", "["}, {"]", "]"}};
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of impossibility to rename Multibranch Pipeline project with invalid data")
    @Test(dataProvider = "wrong-character")
    public void testRenameWithInvalidData(String invalidData, String expectedResult) {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickRename()
                .enterNewName(invalidData)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "‘" + expectedResult + "’ is an unsafe character");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence Preview of description for Multibranch Pipeline Project from the Project page")
    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String previewText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewText, DESCRIPTION);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Configuration Page" +
            " by click 'Configure the project'")
    @Test
    public void testConfigureProject() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String configurationHeaderText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigureProject()
                .getPageHeaderText();

        Assert.assertEquals(configurationHeaderText, "Configuration");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Scan Multibranch Pipeline Page" +
            " by click 'Re-index branches'")
    @Test
    public void testReindexBranches() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String headerScanMultibranchPipeline = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickReindexBranchesLink()
                .getPageHeaderText();

        Assert.assertEquals(headerScanMultibranchPipeline, "Scan Multibranch Pipeline");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Pipeline Page" +
            " by click 'Creating a Jenkins Pipeline'")
    @Test
    public void testCreatingJenkinsPipeline() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String textFromJenkinsPipelinePage = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickCreatingAJenkinsPipelineLinkOnProjectPage()
                .getTextPipelineTitle();

        Assert.assertEquals(textFromJenkinsPipelinePage, "Pipeline");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Branches and Pull Requests Page" +
            " by click 'Creating Multibranch Projects'")
    @Test
    public void testCreatingMultibranchProjectsLink() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String pageHeaderText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickMultibranchProject()
                .getPageHeaderText();

        Assert.assertEquals(pageHeaderText, "Branches and Pull Requests");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Scan Multibranch Pipeline Log Page" +
            " from side menu for Multibranch Pipeline Project")
    @Test
    public void testScanMultibranchPipelineLog() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String pageHeaderText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickScanLog()
                .getPageHeaderText();

        Assert.assertEquals(pageHeaderText, "Scan Multibranch Pipeline Log");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Multibranch Pipeline Events Page" +
            " from side menu for Multibranch Pipeline Project")
    @Test
    public void testMultibranchPipelineEvents() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String pageHeaderText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickEventsLink()
                .getPageHeaderText();

        Assert.assertEquals(pageHeaderText, "Multibranch Pipeline Events");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to People Page" +
            " from side menu for Multibranch Pipeline Project")
    @Test
    public void testNavigateToPeoplePageFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String peoplePageText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickPeopleFromSideMenu()
                .getPageHeaderText();

        Assert.assertEquals(peoplePageText, "People - Welcome");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Build History of Welcome Page" +
            " from side menu for Multibranch Pipeline Project")
    @Test
    public void testNavigateToBuildHistoryPageFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String buildHistoryWelcomeText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickBuildHistoryWelcomeFromSideMenu()
                .getPageHeaderText();

        Assert.assertEquals(buildHistoryWelcomeText, "Build History of Welcome");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Pipeline Syntax Page" +
            " from side menu for Multibranch Pipeline Project and added option 'sleep: Sleep'")
    @Test
    public void testPipelineSyntax() {
        final String sampleStep = "sleep: Sleep";
        final String time = "1000";
        final String unit = "MILLISECONDS";
        final String expectedScript = "sleep time: " + time + ", unit: '" + unit + "'";
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String pipelineSyntax = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickPipelineSyntax()
                .setSampleStep(sampleStep)
                .enterSleepTime(time)
                .setUnit(unit)
                .clickGeneratePipelineScriptButton()
                .getTextPipelineScript();

        Assert.assertEquals(pipelineSyntax, expectedScript);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Navigation")
    @Description("Verification of possibility to navigate to Credentials Page " +
            "from side menu for Multibranch Pipeline Project")
    @Test
    public void testCredentials() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String credentialsText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickCredentials()
                .getPageHeaderText();

        Assert.assertEquals(credentialsText, "Credentials");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Organization Folder' can be disable by click on the 'Disable Multibranch Pipeline' button on the Project page")
    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String disabledText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickDisableEnableButton()
                .getTextFromDisableMessage();

        Assert.assertEquals(disabledText.substring(0, 47), "This Multibranch Pipeline is currently disabled");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to enable disabled Multibranch Pipeline Project from Project Page")
    @Test
    public void testEnableFromProjectPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String disableButton = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickDisableEnableButton()
                .clickDisableEnableButton()
                .getDisableButtonText();

        boolean iconMultibranch = new MultibranchPipelinePage(getDriver())
                .isMetadataFolderIconDisplayed();

        Assert.assertEquals(disableButton, "Disable Multibranch Pipeline");
        Assert.assertTrue(iconMultibranch, "The Metadata Folder Icon is not displayed on Multibranch Pipeline's page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Configuration Page from drop-down menu for Multibranch Pipeline Project")
    @Test
    public void testAccessConfigurationPageFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String getHeaderText = new MainPage(getDriver())
                .clickConfigureDropDown(
                        NAME, new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver())))
                .getHeaderText();

        Assert.assertEquals(getHeaderText, "Configuration");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verification of possibility to navigate to Configuration Page from side menu menu for Multibranch Pipeline Project")
    @Test
    public void testAccessConfigurationPageFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String getHeaderText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .getHeaderText();

        Assert.assertEquals(getHeaderText, "Configuration");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be disable from Configuration page")
    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualDisableMessage = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickDisable()
                .clickSaveButton()
                .getTextFromDisableMessage();
        Assert.assertTrue(actualDisableMessage.contains("This Multibranch Pipeline is currently disabled"),
                "The info message of the disabled Multibranch Pipeline does not contain 'This Multibranch Pipeline is currently disabled'");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be enable from Configuration page")
    @Test
    public void testEnableFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String enableMultibranch = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickDisable()
                .clickSaveButton()
                .clickConfigure()
                .clickDisable()
                .clickSaveButton()
                .getDisableButtonText();

        Assert.assertEquals(enableMultibranch.trim(), "Disable Multibranch Pipeline");
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("Verification of presence Preview of description for Multibranch Pipeline Project from the Configuration page")
    @Test
    public void testPreviewDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String previewText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickPreview()
                .getPreviewText();

        Assert.assertEquals(previewText, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The 'Description' can be added to the Multibranch Pipeline from Configuration page")
    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String MultibranchPipeline = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .addDescription(DESCRIPTION)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .getAddedDescriptionFromConfig();

        Assert.assertEquals(MultibranchPipeline, DESCRIPTION);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Function")
    @Description("The 'Display name' can be added to the Multibranch Pipeline from Configuration page")
    @Test
    public void testAddDisplayName() {
        final String multibranchPipelineDisplayName = "MultibranchDisplayName";
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);


        MultibranchPipelinePage multibranchPipelinePage = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .enterDisplayName(multibranchPipelineDisplayName)
                .clickSaveButton();

        Assert.assertEquals(multibranchPipelinePage.getJobName(), multibranchPipelineDisplayName);
        Assert.assertTrue(multibranchPipelinePage.isMetadataFolderIconDisplayed(), "The Metadata Folder Icon is not displayed on MultibranchPipeline page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("All options are displayed from 'Add source'")
    @Test
    public void testBranchSourcesOptionsList() {
        final List<String> optionsList = List.of("Git", "GitHub", "Single repository & branch");
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        List<String> actualOptionsList = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickAddSourceButton()
                .getAddSourceOptionsList();

        Assert.assertEquals(actualOptionsList, optionsList);
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("UI")
    @Description("All options are displayed from Scan Multibranch Pipeline Triggers Interval select")
    @Test
    public void testScanMultibranchPipelineTriggersIntervalsList() {
        final List<String> intervalsList = List.of("1 minute", "2 minutes", "3 minutes", "5 minutes",
                "10 minutes", "15 minutes", "20 minutes", "25 minutes", "30 minutes", "1 hour", "2 hours", "4 hours",
                "8 hours", "12 hours", "1 day", "2 days", "1 week", "2 weeks", "4 weeks");
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        List<String> actualIntervalsList = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickPeriodicallyOtherwiseCheckBox()
                .openIntervalDropDownSelect()
                .getIntervalsList();

        Assert.assertEquals(actualIntervalsList, intervalsList);
    }

    @Severity(SeverityLevel.TRIVIAL)
    @Feature("UI")
    @Description("The 'Appearance' icon can be added to the Multibranch Pipeline from Configuration page " +
            "and can be choose Default icon")
    @Test
    public void testChooseDefaultIcon() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean defaultIconDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickAppearance()
                .selectDefaultIcon()
                .clickSaveButton()
                .isDefaultIconDisplayed();

        Assert.assertTrue(defaultIconDisplayed, "The default Icon is not displayed on MultibranchPipeline page");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'child health metrics' can be added to Multibranch Pipeline")
    @Test
    public void testAddHealthMetrics() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean healthMetricIsVisible = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .healthMetricIsVisible();

        Assert.assertTrue(healthMetricIsVisible, "Health Metric is not displayed");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'child health metrics' can be deleted to Multibranch Pipeline")
    @Test
    public void testDeleteHealthMetrics() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean healthMetric = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .addHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .removeHealthMetrics()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .isHealthMetricInvisible();

        Assert.assertTrue(healthMetric, "The deleted Health Metric is visible");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("The 'Health of the primary branch of a repository' can be added to Multibranch Pipeline")
    @Test
    public void testAddHealthOfThePrimaryBranchOfARepository() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean IsHealthMetricVisible = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickHealthMetrics()
                .addHealthMetricPrimaryBranchRepository()
                .clickSaveButton()
                .clickConfigure()
                .clickHealthMetrics()
                .healthMetricIsVisible();

        Assert.assertTrue(IsHealthMetricVisible, "Health metric is not displayed!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that deleting 'Multibranch Pipeline' can be canceled from drop-down menu on the Main page")
    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean isJobDisplayed = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(NAME)
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(isJobDisplayed, "The Multibranch Pipeline`s name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that deleting 'Multibranch Pipeline' can be canceled from side menu on the Project page")
    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        boolean isJobDisplayed = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickDeleteJobLocatedOnFolderPage()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(NAME);

        Assert.assertTrue(isJobDisplayed, "The Multibranch Pipeline`s name is not displayed on Dashboard from Home page");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be deleted with the 'Delete' option from drop-down menu on the Main page")
    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String WelcomeJenkinsPage = new MainPage(getDriver())
                .dropDownMenuClickDeleteFolders(NAME)
                .clickYesButton()
                .getWelcomeText();

        Assert.assertEquals(WelcomeJenkinsPage, "Welcome to Jenkins!");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Feature("Function")
    @Description("Verify that the 'Multibranch Pipeline' can be deleted with the 'Delete' button from side menu on the Project page")
    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String welcomeText = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickDeleteJobLocatedOnMainPage()
                .clickYesButton()
                .getWelcomeText();

        Assert.assertEquals(welcomeText, "Welcome to Jenkins!");
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Function")
    @Description("Verify that when you click the 'Pipeline: Multibranch' link after clicking the 'Help for Feature: Script Path', you are taken to the 'Pipeline: Multibranch' page")
    @Test
    public void testHelpForFeatureButton() {
        final String expectedTitle = "Pipeline: Multibranch";

        TestUtils.createJob(this, NAME, TestUtils.JobType.MultibranchPipeline, true);

        String actualTitle = new MainPage(getDriver())
                .clickJobName(NAME, new MultibranchPipelinePage(getDriver()))
                .clickConfigure()
                .clickBuildConfiguration()
                .clickScriptPathButton()
                .clickPipelineMultibranchPageLink()
                .switchToWorkflowMultibranchPage()
                .getWorkflowMultibranchPageTitle();

        Assert.assertEquals(actualTitle, expectedTitle);
    }
}
