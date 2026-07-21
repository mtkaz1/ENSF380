# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

# Functions for circle operations
def area(radius, units):
    return f"{3.14 * radius ** 2} {units}²"

def perimeter(radius, units):
    return f"{2 * 3.14 * radius} {units}"

def describe(radius, colour, material):
    return f"A {colour} circle made of {material} with a radius of {radius}"

# Define arrays for circle attributes
radii = [5, 7, 3, 9, 6, 4, 8, 10, 2, 12]
colours = ["red", "blue", "green", "yellow", "purple", "orange", "pink", "white", "black", "brown"]
materials = ["plastic", "metal", "wood", "rubber", "glass", "ceramic", "stone", "paper", "foam", "leather"]
units = ["cm"] * 10  # All circles use the same units

# Print details for each circle
print("Intermediate Version with Arrays for Attributes:")
for i in range(len(radii)):
    print(describe(radii[i], colours[i], materials[i]))
    print("Area:", area(radii[i], units[i]))
    print("Perimeter:", perimeter(radii[i], units[i]))
    print()
