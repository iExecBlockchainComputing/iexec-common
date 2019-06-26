pipeline {
    
    agent any

    stages {

        // code quality is triggered only on master branch
        stage('Test + Code quality (master only)') {
            when {
                branch 'master'
            }
            steps {
                 withCredentials([
                 string(credentialsId: 'ADDRESS_SONAR', variable: 'address_sonar'),
                 string(credentialsId: 'SONAR_COMMON_TOKEN', variable: 'common_token')]){
                    sh './gradlew clean test sonarqube -Dsonar.projectKey=iexec-common -Dsonar.host.url=$address_sonar -Dsonar.login=$common_token --no-daemon'
                 }
                 junit 'build/test-results/**/*.xml'
            }
        }

        stage('Test') {
           when {
               not {
                   branch 'master'
               }
           }
           steps {
               sh './gradlew clean test --no-daemon'
               junit 'build/test-results/**/*.xml'
           }
       }

        stage('Build') {
            steps {
                sh './gradlew build --no-daemon'
            }
        }

        stage('Upload Archive') {
            when {
                branch 'master'
            }
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD']]) {
                    sh './gradlew uploadArchives -PnexusUser=$NEXUS_USER -PnexusPassword=$NEXUS_PASSWORD --no-daemon'
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