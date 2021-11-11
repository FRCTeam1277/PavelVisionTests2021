#!/usr/bin/env python3

# Copyright (c) FIRST and other WPILib contributors.
# Open Source Software; you can modify and/or share it under the terms of
# the WPILib BSD license file in the root directory of this project.


import cv2
import numpy
import time
import math
from enum import Enum

from networktables import NetworkTables

NetworkTables.initialize()
vision_nt = NetworkTables.getTable('Vision')

faceArray = vision_nt.getEntry("facereg")

def main():


    vid = cv2.VideoCapture(0)


    pipeline = GripPythonPipeline()
    classif = cv2.CascadeClassifier("haarcascade_frontalface_alt.xml")
    while True:
        time2, input_img = vid.read()

        if time2 == 0:
            continue

        pipeline.process(input_img, classif)
        if len(pipeline.cascade_classifier_output) >= 1:
            faceArray.setNumber(pipeline.cascade_classifier_output[0][3]) #gives width of first face to stream, used to determine if it exists (you can expand on this)
        else:
            faceArray.setNumber(0)


##
# AUTO GENERATED, DON'T TOUCH
# Not seperated since I can only give one file
##


class GripPythonPipeline:
    """
    An OpenCV pipeline generated by GRIP.
    """

    def __init__(self):
        """initializes all values to presets or None if need to be set
        """
        self.__new_size_width = 100.0
        self.__new_size_height = 100.0

        self.new_size_output = self.__new_size(self.__new_size_width, self.__new_size_height)



        self.__cascade_classifier_scale_factor = 1.1
        self.__cascade_classifier_min_neighbors = 2
        self.__cascade_classifier_min_size = self.new_size_output
        self.__cascade_classifier_max_size = (0, 0)

        self.cascade_classifier_output = None


    def process(self, source0, source1):
        """
        Runs the pipeline and sets all outputs to new values.
        """

        # Step Cascade_Classifier0:
        self.__cascade_classifier_image = source0
        self.__cascade_classifier_classifier = source1
        self.__cascade_classifier_min_size = self.new_size_output
        (self.cascade_classifier_output) = self.__cascade_classifier(self.__cascade_classifier_image, self.__cascade_classifier_classifier, self.__cascade_classifier_scale_factor, self.__cascade_classifier_min_neighbors, self.__cascade_classifier_min_size, self.__cascade_classifier_max_size)


    @staticmethod
    def __new_size(width, height):
        """Fills a size with given width and height.
		Args:
            width: A number for the width.
            height: A number for the height.
        Returns:
            A list of two numbers that represent a size.
        """
        return (width, height)

    @staticmethod
    def __cascade_classifier(input, classifier, scale_factor, min_neighbors, min_size, max_size):
        """Sets the values of pixels in a binary image to their distance to the nearest black pixel.
        Args:
            input: A numpy.ndarray.
            classifier: The classifier to use
            scale_factor: the scale factor of each successive downsized image
            min_neighbors: how many neighbors each candidate rectangle should have to retain it
            min_size: the minimum possible object size
            max_size: the maximum possible object size. If (0, 0), it is assumed to be unbounded
        Return:
            A list of rectangles bounding the found regions of interest
        """
        return classifier.detectMultiScale(input,scale_factor,min_neighbors)





main()


