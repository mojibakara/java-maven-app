#!/usr/bin/env groovy

library identifier: 'shared-library@main', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/mojibakara/shared_library.git',
         credentialsId: 'github'
        ]
)


def gv

    pipeline {
        agent any
        tools {
            maven 'maven'
        }
        stages {
            stage ("build jar") {
                steps {
                    script {
                        buildJar()
                    }
                }
            }
            stage ("build image and push image") {
                steps {
                    script {
                        buildImage '185.110.189.54:8084/java-maven-app:1.8'
                        dockerLogin()
                        dockerPush '185.110.189.54:8084/java-maven-app:1.8'
                    }
                }
            }
            stage ("deploy") {
                steps {
                    script {
                        gv.deployApp()
                    }
                }
            }
        }
    }
