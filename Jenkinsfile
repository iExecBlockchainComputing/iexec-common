@Library('global-jenkins-library@feature/sonar-pro-migration') _
buildJavaProject(
    buildInfo: getBuildInfo(),
    integrationTestsEnvVars: [],
    shouldPublishJars: true,
    shouldPublishDockerImages: false,
    notifyJobs: ['iexec-core', 'iexec-worker']
)
