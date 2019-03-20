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
                script {
                    sh './gradlew clean build' //run a gradle task
                }
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
}