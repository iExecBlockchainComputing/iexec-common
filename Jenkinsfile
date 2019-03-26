pipeline {
    
    agent any

    stages {

        stage('Test') {
            steps {
                 sh './gradlew clean test'
                 junit 'build/test-results/**/*.xml'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Upload Archive') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD']]) {
                    sh './gradlew uploadArchives -PnexusUser=$NEXUS_USER -PnexusPassword=$NEXUS_PASSWORD'
                }
                archiveArtifacts artifacts: 'build/libs/*.jar'
            }
        }

        stage ("Notify iexec-core") {
            when {
                branch 'master'
            }
            steps {
                build job: 'iexec-core/master', propagate: true, wait: false
            }
        }

        stage ("Notify iexec-worker") {
            when {
                branch 'master'
            }
            steps {
                build job: 'iexec-worker/master', propagate: true, wait: false
            }
        }

    }
    
}