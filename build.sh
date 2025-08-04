#!/bin/bash

# Simple build script for BD-JB-1250-lapse
# This builds the Java classes and creates a BD-J compatible JAR

echo "Building BD-JB-1250-lapse..."

# Clean previous build
echo "Cleaning previous build..."
rm -rf src/**/*.class *.jar discdir/ *.iso

# Compile Java sources with LuaJ
echo "Compiling Java sources..."
javac -cp lib/luaj-jse-3.0.1.jar \
    src/javax/microedition/xlet/*.java \
    src/javax/tv/xlet/*.java \
    src/org/havi/ui/*.java \
    src/com/sun/xlet/*.java \
    src/org/bdj/*.java \
    src/org/bdj/sandbox/*.java \
    src/org/bdj/api/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi

echo "Compilation successful!"

# Create JAR file
echo "Creating JAR file..."
jar cf bdj-exploit.jar -C src/ .

if [ $? -eq 0 ]; then
    echo "JAR created successfully: bdj-exploit.jar"
    echo "Size: $(ls -lh bdj-exploit.jar | awk '{print $5}')"
else
    echo "JAR creation failed!"
    exit 1
fi

echo "Build completed successfully!"
echo ""
echo "To create a BD-J disc:"
echo "1. Copy bdj-exploit.jar to a BD-J disc structure"
echo "2. Or use the included Makefile to create a complete ISO"
echo ""
echo "Don't forget to prepare your USB stick with:"
echo "- payload.bin (HEN/GoldHEN for your firmware)"
echo "- inject_hen.lua (from this repo)" 
echo "- savedata/ folder (from this repo)"