@Library('global-jenkins-library@2.1.1') _
buildJavaProject(
    buildInfo: getBuildInfo(),
    integrationTestsEnvVars: [],
    shouldPublishJars: true,
    shouldPublishDockerImages: false,
    notifyJobs: ['iexec-core', 'iexec-worker']
)
