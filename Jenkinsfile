@Library('global-jenkins-library@feature/infra-migration') _
buildJavaProject(
    buildInfo: getBuildInfo(),
    integrationTestsEnvVars: [],
    shouldPublishJars: true,
    shouldPublishDockerImages: false,
    notifyJobs: ['iexec-core', 'iexec-worker']
)
