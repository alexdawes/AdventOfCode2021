using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace AoC._01
{
    public sealed class Solution : ISolution
    {
        public async Task Run(bool test)
        {
            var part1 = await Part1(test);
            Console.WriteLine($"Part 1: {part1}");

            var part2 = await Part2(test);
            Console.WriteLine($"Part 2: {part2}");
        }

        private async Task<int> Part1(bool test)
        {
            var inputs = await ParseInput(test);

            return CountIncreases(inputs);
        }
        
        private async Task<int> Part2(bool test)
        {
            var inputs = await ParseInput(test);
            Console.WriteLine(string.Join(" ", inputs));

            var rolling = GetRollingValues(inputs);
            Console.WriteLine(string.Join(" ", rolling));

            return CountIncreases(rolling);
        }

        private List<int> GetRollingValues(List<int> input)
        {
            return Enumerable.Range(0, input.Count - 2).Select(i => input.Skip(i).Take(3).Sum()).ToList();
        }
        
        private int CountIncreases(List<int> input)
        {
            var count = 0;
            var previous = input.First();
            foreach (var current in input.Skip(1))
            {
                if (current > previous)
                {
                    count++;
                }

                previous = current;
            }

            return count;
        }
        
        private async Task<List<int>> ParseInput(bool test)
        {
            return (await File.ReadAllTextAsync(test ? "01/test_input" : "01/input"))
                .Split('\n')
                .Select(int.Parse)
                .ToList();
        }
    }
}