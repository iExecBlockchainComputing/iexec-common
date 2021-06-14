pipeline {
    
    agent any

    stages {

        stage('Test') {
            steps {
                withCredentials([
                string(credentialsId: 'ADDRESS_SONAR', variable: 'address_sonar'),
                string(credentialsId: 'SONAR_COMMON_TOKEN', variable: 'common_token'),
                [$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus',
                        usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD'],
                [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub',
                        usernameVariable: 'DOCKER_IO_USER', passwordVariable: 'DOCKER_IO_PASSWORD']]){
                    sh ' NEXUS_REGISTRY=nexus.iex.ec NEXUS_USER=$NEXUS_USER NEXUS_PASSWORD=$NEXUS_PASSWORD DOCKER_IO_USER=$DOCKER_IO_USER DOCKER_IO_PASSWORD=$DOCKER_IO_PASSWORD ./gradlew clean test sonarqube -Dsonar.projectKey=iexec-common -Dsonar.host.url=$address_sonar -Dsonar.login=$common_token --no-daemon'
                }
                junit 'build/test-results/**/*.xml'
            }
        }

        stage('Build') {
            steps {
                // withCredentials([
                //         [$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus',
                //                 usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD'],
                //         [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub',
                //                 usernameVariable: 'DOCKER_IO_USER', passwordVariable: 'DOCKER_IO_PASSWORD']]) {
                    // sh 'NEXUS_REGISTRY=nexus.iex.ec NEXUS_USER=$NEXUS_USER NEXUS_PASSWORD=$NEXUS_PASSWORD DOCKER_IO_USER=$DOCKER_IO_USER DOCKER_IO_PASSWORD=$DOCKER_IO_PASSWORD ./gradlew build --no-daemon'
                // }

                sh './gradlew build -x test --no-daemon'
            }
        }

        stage('Upload Archive') {
            when {
                anyOf{
                    branch 'master'
                    branch 'develop'
                }
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
                anyOf{
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                build job: 'iexec-core/'+ env.BRANCH_NAME, propagate: true, wait: false
            }
        }

        stage ("Notify iexec-worker") {
            when {
                anyOf{
                    branch 'master'
                    branch 'develop'
                }
            }
            steps {
                build job: 'iexec-worker/'+ env.BRANCH_NAME, propagate: true, wait: false
            }
        }

    }
    
}