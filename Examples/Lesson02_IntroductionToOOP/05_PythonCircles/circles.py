# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

# This code contains a deliberate error which is discussed in the lecture.

# Functions for circle operations
def area(radius, units):
    return f"{3.14 * radius ** 2} {units}²"

def perimeter(radius, units):
    return f"{2 * 3.14 * radius} {units}"

def describe(radius, colour, material):
    return f"A {colour} circle made of {material} with a radius of {radius}"

# Define circles as dictionaries
circle1 = {"radius": 5, "colour": "red", "material": "plastic", "units": "cm"}
circle2 = {"radius": 7, "colour": "blue", "material": "metal", "units": "cm"}
circle3 = {"radius": 3, "colour": "green", "material": "wood", "units": "cm"}
circle4 = {"radius": 9, "colour": "yellow", "material": "rubber", "units": "cm"}
circle5 = {"radius": 6, "colour": "purple", "material": "glass", "units": "cm"}
circle6 = {"radius": 4, "colour": "orange", "material": "ceramic", "units": "cm"}
circle7 = {"radius": 8, "colour": "pink", "material": "stone", "units": "cm"}
circle8 = {"radius": 10, "color": "white", "material": "paper", "units": "cm"}
circle9 = {"radius": 2, "colour": "black", "material": "foam", "units": "cm"}
circle10 = {"radius": 12, "colour": "brown", "material": "leather", "units": "cm"}

# Group all circles into an array
circles = [circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8, circle9, circle10]

# Print details for each circle
for circle in circles:
    print(describe(circle["radius"], circle["colour"], circle["material"]))
    print("Area:", area(circle["radius"], circle["units"]))
    print("Perimeter:", perimeter(circle["radius"], circle["units"]))
    print()
