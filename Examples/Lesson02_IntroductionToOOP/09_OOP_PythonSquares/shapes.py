# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

class Circle:
    def __init__(self, radius, colour):
        self.radius = radius
        self.colour = colour

    def area(self):
        return 3.14 * self.radius ** 2

    def perimeter(self):
        return 2 * 3.14 * self.radius

    def describe(self):
        return f"A {self.colour} circle with radius {self.radius}"


class Square:
    def __init__(self, radius, colour):
        self.radius = radius
        self.colour = colour

    def area(self):
        return self.radius ** 2

    def perimeter(self):
        return 4 * self.radius

    def describe(self):
        return f"A {self.colour} square with side length {self.radius}"


# Create instances of Circle and Square
circle = Circle(5, "red")
square = Square(4, "blue")

print("OOP Example:")
print(circle.describe())
print("Area:", circle.area())
print("Perimeter:", circle.perimeter())

print(square.describe())
print("Area:", square.area())
print("Perimeter:", square.perimeter())

