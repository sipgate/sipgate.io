# Example project file for a sipgate.io HTTP server
TEMPLATE = app
TARGET = example
INCLUDEPATH += .

# Dependency to the NuriaProject Framework
# See https://github.com/NuriaProject/Framework
CONFIG += nuria
NURIA += core network

# 
SOURCES += example.cpp
