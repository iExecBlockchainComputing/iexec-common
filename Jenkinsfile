pipeline {
    
    agent any
    //agent {
    //    label 'jenkins-agent-docker'
    //}

    //triggers { pollSCM('') //polling for changes, here once a minute }

    stages {

        //stage('Checkout') { steps{ checkout changelog: true, poll: true, scm: [$class: 'GitSCM', branches: [[name: '*/jenkins']]] } }

        stage('Build') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD']]) {
                    sh './gradlew build uploadArchives -PnexusUser=$NEXUS_USER -PnexusPassword=$NEXUS_PASSWORD'
                }
            }
        }

        stage ("Trigger core") {
            steps {
                build job: 'iexec-core/jenkins', propagate: true, wait: false
            }
        }

        //stage('Checkout Worker') {
        //    steps{
        //        checkout changelog: true, poll: true, scm: [$class: 'GitSCM', branches: [[name: '*/jenkins']], userRemoteConfigs: [[credentialsId: 'github', url: 'https://github.com/iExecBlockchainComputing/iexec-worker.git']]]
        //    }
        //
        //}

        //stage('Unit & Integration Tests') {
        //    steps {
        //        script {
        //            sh './gradlew clean build -x test' //run a gradle task
        //        }
        //    }
        //}

        
        //stage('Push on Docker Hub') {
        //    steps {
        //        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'USER', passwordVariable: 'PASSWORD']]) {
        //            sh './gradlew -PdockerHubUser=$USER -PdockerHubPassword=$PASSWORD pushImage'
        //        }
        //    }
        //}

        //stage('Trigger worker') {
        //    steps {
        //        script{
        //            def rootDir = pwd()
        //            println("Current Directory: " + rootDir)

        //            def jenkinsfileWorker = load "${rootDir}/JenkinsfileWorker"

        //            jenkinsfileWorker.start()
        //        }

        //    }
        //}
    }

    post {
        always {
            junit 'build/reports/**/*.xml'
        }
    }
}